// Importes
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import java.time.LocalDate;

// Declaração da classe CartaoCredito 
public class CartaoCredito {
    // Declaração dos atributos da classe
    private String CPF;
    private String numeroBandeira;
    private String numeroBanco;
    private String numeroContaCliente;
    private String digitoVerificador;
    private String numeroCartao;
    private String nomeCliente;
    private int codigoSeguranca;
    private int qntFaturas = 0;
    private double limite;
    private ArrayList<Double> faturas;
    private Calendar dataCriacao;
    private Calendar dataValidade;

    // Construtor da classe
    public CartaoCredito(String CPF, String numeroBandeira, String numeroBanco, String numeroContaCliente,
            String digitoVerificador, int qntFaturas, double limite,
            String nome, String... sobreNomes) {
        // Inicialização dos atributos com os valores passados como parâmetro
        this.CPF = CPF;
        this.numeroBandeira = numeroBandeira;
        this.numeroBanco = numeroBanco;
        this.numeroContaCliente = numeroContaCliente;
        this.digitoVerificador = digitoVerificador;
        this.codigoSeguranca = codigoSegurancaFormatoCartao(); // Gera um código de segurança
        this.limite = limite;
        this.faturas = new ArrayList<Double>(); // Inicializa a lista de faturas

        // Obtém a data atual
        LocalDate dataAtual = LocalDate.now();
        int ano = dataAtual.getYear();
        int mes = dataAtual.getMonthValue();
        int dia = dataAtual.getDayOfMonth();

        this.dataCriacao = Calendar.getInstance();
        dataCriacao.set(ano, mes, dia); // Define a data de criação do cartão

        this.dataValidade = dataVencimentoCartao(); // Calcula a data de validade do cartão
        this.numeroCartao = numeroFormatoCartao(numeroBandeira, numeroBanco, numeroContaCliente, digitoVerificador); // Formata o número do cartão

        nomeFormatoCartao(nome, sobreNomes); // Formata o nome do cliente
    }

    // Método privado para formatar o nome do cliente
    private void nomeFormatoCartao(String nome, String... sobreNomes) {
        // Inicializa o nome do cliente com o primeiro nome recebido e um espaço
        this.nomeCliente = nome + " ";
        // Calcula o tamanho da lista de sobrenomes
        int tamanho = sobreNomes.length - 1;

        // Loop para iterar sobre os sobrenomes
        for (int i = 0; i <= tamanho; i++) {
            // Divide o sobrenome atual em partes separadas
            String[] sobreNome = sobreNomes[i].split(" ");
            // Verifica se o sobrenome atual é comum e não deve ser abreviado
            boolean condicao = sobreNome[0].equals("dos") || sobreNome[0].equals("das") ||
                    sobreNome[0].equals("do") || sobreNome[0].equals("da") ||
                    sobreNome[0].equals("de") || sobreNome[0].equals("des");

            // Se for o último sobrenome e for uma condição comum
            if (i >= tamanho && condicao) {
                // Adiciona apenas o segundo nome e converte tudo para maiúsculas
                this.nomeCliente += sobreNome[1];
                this.nomeCliente = this.nomeCliente.toUpperCase();
                // Sai do loop
                break;
            }
            // Se for o último sobrenome e não for uma condição comum
            else if (i >= tamanho) {
                // Adiciona o sobrenome completo e converte tudo para maiúsculas
                this.nomeCliente += sobreNome[0];
                this.nomeCliente = this.nomeCliente.toUpperCase();
                // Sai do loop
                break;
            }

            // Se o sobrenome atual for uma condição comum
            if (condicao) {
                // Adiciona apenas a primeira letra do segundo nome abreviada com um ponto
                this.nomeCliente += sobreNome[1].charAt(0) + ". ";
            }
            // Se o sobrenome atual não for uma condição comum
            else {
                // Adiciona apenas a primeira letra do sobrenome abreviada com um ponto
                this.nomeCliente += sobreNome[0].charAt(0) + ". ";
            }
        }
    }

    // Método privado para formatar o número do cartão
    private String numeroFormatoCartao(String numeroBandeira, String numeroBanco, String numeroContaCliente,
            String digitoVerificador) {
        // Concatena todos os números em uma única string
        String numeroJunto = numeroBandeira + numeroBanco + numeroContaCliente + digitoVerificador;
        String numeroFormatado = ""; // Inicializa a string que irá conter o número formatado

        // Percorre os dígitos do número concatenado
        for (int i = 1; i <= 16; i++) {
            numeroFormatado += numeroJunto.charAt(i - 1); // Adiciona o próximo dígito ao número formatado

            // Adiciona um espaço a cada 4 dígitos
            if (i % 4 == 0) {
                numeroFormatado += " ";
            }
        }

        return numeroFormatado; // Retorna o número formatado
    }

    // Método privado para calcular a data de vencimento do cartão (5 anos a partir
    // da data de criação)
    private Calendar dataVencimentoCartao() {
        Calendar dataVencimento = Calendar.getInstance(); // Obtém uma instância do objeto Calendar

        LocalDate dataAtual = LocalDate.now(); // Obtém a data atual
        int ano = dataAtual.getYear(); // Obtém o ano atual
        int mes = dataAtual.getMonthValue(); // Obtém o mês atual
        int dia = dataAtual.getDayOfMonth(); // Obtém o dia atual

        // Define a data de vencimento para a data atual
        // Note que os meses em Calendar começam do zero (janeiro é 0, fevereiro é 1,
        // etc.)
        dataVencimento.set(ano, mes - 1, dia); // O mês é subtraído de 1 para se adequar ao formato do Calendar

        // Adiciona 5 anos à data de vencimento
        dataVencimento.add(Calendar.YEAR, 5);

        return dataVencimento; // Retorna o objeto Calendar com a data de vencimento calculada
    }

    // Método privado para gerar um código de segurança aleatório
    private int codigoSegurancaFormatoCartao() {
        return (int) (Math.random() * 999);
    }

    // Método privado para ordenar as faturas
    private void ordenarFaturas() {
        double aux;

        // Percorre a lista de faturas
        for (int i = 0; i < this.faturas.size(); i++) {
            for (int j = i + 1; j < this.faturas.size(); j++) { // Começa da próxima posição após i
                // Compara o valor da fatura em i com o valor da fatura em j
                if (this.faturas.get(i) > this.faturas.get(j)) { // Se a fatura em i for maior que a fatura em j
                    // Troca os valores de posição para ordenar em ordem crescente
                    aux = this.faturas.get(i);
                    this.faturas.set(i, this.faturas.get(j));
                    this.faturas.set(j, aux);
                }
            }
        }
    }

    // Método para realizar uma compra no cartão de crédito
    public void compraCredito(double valorCompra) {
        if (valorCompra < this.limite) { // Verifica se o valor da compra é menor que o limite de crédito disponível
            this.qntFaturas++; // Incrementa a quantidade de faturas
            this.limite -= valorCompra; // Reduz o limite de crédito disponível pelo valor da compra
            this.faturas.add(valorCompra); // Adiciona o valor da compra à lista de faturas
            ordenarFaturas(); // Ordena as faturas após adicionar a nova compra
        } else {
            // Imprime uma mensagem de transação negada se o valor da compra exceder o
            // limite de crédito
            JOptionPane.showMessageDialog(null, "Transação negada!");
        }
    }

    // Método para realizar uma compra parcelada no cartão de crédito
    public void compraCredito(double valorCompra, int quantidadeParcelas) {
        if (valorCompra < this.limite) { // Verifica se o valor total da compra é menor que o limite de crédito
                                         // disponível
            this.limite -= valorCompra; // Reduz o limite de crédito disponível pelo valor total da compra

            // Adiciona cada parcela da compra às faturas
            for (int i = this.qntFaturas; i < quantidadeParcelas + this.qntFaturas; i++) {
                this.faturas.add(valorCompra / quantidadeParcelas); // Divide o valor total da compra pelo número de
                                                                    // parcelas
            }

            ordenarFaturas(); // Ordena as faturas após adicionar as parcelas da compra
            this.qntFaturas = this.faturas.size(); // Atualiza a quantidade de faturas registradas
        } else {
            // Imprime uma mensagem de transação negada se o valor da compra exceder o
            // limite de crédito
            JOptionPane.showMessageDialog(null, "Transação negada!");
        }
    }

    // Método para realizar o pagamento de uma fatura do cartão de crédito
    public void pagamento(double valorPagamento) {
        double valorFatura;
        double valorPago;
        double resto;

        // Itera sobre as faturas, começando pela mais recente
        for (int i = this.qntFaturas - 1; i >= 0; i--) {
            valorFatura = this.faturas.get(i); // Obtém o valor da fatura atual

            if (valorPagamento >= valorFatura) { // Se o valor do pagamento for maior ou igual ao valor da fatura
                resto = Math.abs(valorPagamento - valorFatura); // Calcula o resto do pagamento após pagar a fatura
                valorPago = Math.abs(valorPagamento - resto); // Calcula o valor realmente pago

                faturas.set(i, valorFatura - valorPago); // Atualiza o valor da fatura subtraindo o valor pago
                valorPagamento -= valorPago; // Reduz o valor do pagamento pelo valor pago
                this.limite += valorPago;
            } else { // Se o valor do pagamento for menor que o valor da fatura
                faturas.set(i, valorFatura - valorPagamento); // Paga a fatura completamente
                this.limite += valorPagamento;
                valorPagamento = 0; // Define o valor do pagamento como zero, indicando que não há mais valor a
                                    // pagar
            }
            this.qntFaturas--; // Reduz a quantidade de faturas registradas

            if (valorPagamento == 0.0) { // Se o valor do pagamento for zero, não há mais faturas a pagar
                break;
            }
        }

        ordenarFaturas(); // Ordena as faturas após o pagamento
    }

    public void mostraFaturas() {
        double total = 0; // Inicializa o total das faturas
        String msg = ""; // Inicializa a mensagem de exibição

        // Itera sobre a lista de faturas
        for (int i = 0; i < this.faturas.size(); i++) {
            // Verifica se o valor da fatura é maior que zero (se está pendente de
            // pagamento)
            if (this.faturas.get(i) > 0.0) {
                // Adiciona o valor da fatura à mensagem de exibição formatado com duas casas
                // decimais
                msg += String.format("%.2f", this.faturas.get(i)) + " | ";
                total += faturas.get(i); // Incrementa o total das faturas
            }
        }

        // Exibe as faturas pendentes em uma caixa de diálogo, incluindo o total das
        // faturas
        JOptionPane.showMessageDialog(null, msg + "total: " + String.format("%.2f", total));
    }
    
    public void estorno(Double valorEstorno, int quantidadeParcelas) {
        Double valorParcelas = valorEstorno / quantidadeParcelas;

        // Percorre as faturas ou até que o valor de estorno seja completamente
        // utilizado
        for (int i = 0; (i < this.faturas.size()) && valorEstorno > 0; i++) {
            double fatura = this.faturas.get(i);
            // Verifica se o valor da fatura é igual ao valor de uma parcela do estorno
            if (fatura == valorParcelas) {
                this.faturas.set(i, 0.0); // Marca a fatura como estornada (valor zero)
                valorEstorno -= valorParcelas; // Reduz o valor de estorno pelo valor da parcela estornada
            }
        }

        ordenarFaturas(); // Ordena as faturas após o estorno
    }

     // Método para retornar o CPF do cliente
    public String getCPF() {
        return this.CPF;
    }

    // Método para retornar o número da bandeira do cartão
    public String getNumeroBandeira() {
        return this.numeroBandeira;
    }

    // Método para retornar o número do banco associado ao cartão
    public String getNumeroBanco() {
        return this.numeroBanco;
    }

    // Método para retornar o número da conta do cliente associado ao cartão
    public String getNumeroContaCliente() {
        return this.numeroContaCliente;
    }

    // Método para retornar o dígito verificador do cartão
    public String getDigitoVerificador() {
        return this.digitoVerificador;
    }

    // Método para retornar o número do cartão
    public String getNumeroCartao() {
        return this.numeroCartao;
    }

    // Método para retornar o nome do cliente
    public String getNomeCliente() {
        return this.nomeCliente;
    }

    // Método para retornar o código de segurança do cartão
    public int getCodigoSeguranca() {
        return this.codigoSeguranca;
    }

    // Método para retornar o limite de crédito do cartão
    public Double getLimite() {
        return this.limite;
    }

    // Método para definir o limite de crédito do cartão
    public void setLimite(Double limite) {
        this.limite = limite;
    }

    // Método para retornar a lista de faturas do cartão
    public ArrayList<Double> getFaturas() {
        return this.faturas;
    }

    // Método para retornar a data de criação do cartão
    public Date getDataCriacao() {
        return this.dataCriacao.getTime();
    }

    // Método para retornar a data de validade do cartão
    public Date getDataValidade() {
        return this.dataValidade.getTime();
    }
}