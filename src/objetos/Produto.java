
package objetos;


public class Produto {
    
    // Atributos
    private String nome_produto;
    private String descricao;
    private float preco;

    public String getNome_produto() {
        return nome_produto;
    }

    public void setNome_produto(String nome_produto) {
        this.nome_produto = nome_produto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public void limpaProduto() {
        nome_produto = "";
        descricao = "";
        preco = 0;
    }
    
}
