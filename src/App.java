import javax.swing.JOptionPane;

public class App {
    public static void main(String[] args) throws Exception {

        CartaoCredito cartao = new CartaoCredito(
            "969.851.796-64",
            "5",
            "47682",
            "498509214",
            "4",
            0,
            2000.00,
            "Gustavo",
            "William", "Gonçalves", "da Silva"
        );
        
       // Exibindo informações do cartão
        JOptionPane.showMessageDialog(null, "Número do Cartão: " + cartao.getNumeroCartao());
        JOptionPane.showMessageDialog(null, "Nome do Cliente: " + cartao.getNomeCliente());
        JOptionPane.showMessageDialog(null, "Limite de Crédito: " + cartao.getLimite());
        JOptionPane.showMessageDialog(null, "Data de Criação: " + cartao.getDataCriacao());
        JOptionPane.showMessageDialog(null, "Data de Validade: " + cartao.getDataValidade());

        // Alterando o limite do cartão
        cartao.setLimite(1500.0);
        JOptionPane.showMessageDialog(null, "Novo Limite de Crédito: " + cartao.getLimite());

        // Realizando uma compra no cartão
        double valorCompra = 500.0;
        cartao.compraCredito(valorCompra);
        JOptionPane.showMessageDialog(null, "Compra realizada com sucesso!");

        // Exibindo as faturas pendentes
        JOptionPane.showMessageDialog(null, "Faturas Pendentes: ");
        cartao.mostraFaturas();

        // Realizando um pagamento de uma fatura
        double valorPagamento = 300.0;
        cartao.pagamento(valorPagamento);
        JOptionPane.showMessageDialog(null, "Pagamento realizado com sucesso!");

        // Exibindo as faturas pendentes após o pagamento
        JOptionPane.showMessageDialog(null, "Faturas Pendentes Após Pagamento: ");
        cartao.mostraFaturas();

        // Realizando uma compra parcelada no cartão
        double valorCompraParcelada = 1200.0;
        int quantidadeParcelas = 3;
        cartao.compraCredito(valorCompraParcelada, quantidadeParcelas);
        JOptionPane.showMessageDialog(null, "Compra parcelada realizada com sucesso!");

        // Exibindo as faturas pendentes após a compra parcelada
        JOptionPane.showMessageDialog(null, "Faturas Pendentes Após Compra Parcelada: ");
        cartao.mostraFaturas();

        // Realizando um estorno de uma parcela
        double valorEstorno = 400.0;
        cartao.estorno(valorEstorno, 1);
        JOptionPane.showMessageDialog(null, "Estorno realizado com sucesso!");

        // Exibindo as faturas pendentes após o estorno
        JOptionPane.showMessageDialog(null, "Faturas Pendentes Após Estorno: ");
        cartao.mostraFaturas();
    }
}