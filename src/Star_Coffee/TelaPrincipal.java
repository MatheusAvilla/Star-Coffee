
package Star_Coffee;

import Connection.MySQL;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import objetos.Cliente;
import objetos.Produto;

public class TelaPrincipal extends javax.swing.JFrame {

    MySQL conectar = new MySQL(); //acessar os métodos de conexao com o banco
    Cliente novoCliente = new Cliente(); //acessar os atributos da classe cliente
    Produto novoProduto = new Produto(); //acessar os atributos da classe produto
    
    public TelaPrincipal() {
        initComponents();
    }
    
    double valor = 0;
    
    DecimalFormat df = new DecimalFormat("#.00"); 

    private void cadastraCliente(Cliente novoCliente){
        this.conectar.conectaBanco(); 
        
        novoCliente.setTipoUsuario((String) txtTipoUsuario.getSelectedItem());
        novoCliente.setNome(txtNome.getText());
        novoCliente.setCpf(txtCpf.getText());
        novoCliente.setdataNasc(txtDataNasc.getText());
        novoCliente.setSexo((String) txtSexo.getSelectedItem());
        novoCliente.setEndereco(txtEndereco.getText());
        novoCliente.setCidade(txtCidade.getText());
        novoCliente.setEstado((String) txtEstado.getSelectedItem());
        
        try {
                        
            this.conectar.insertSQL("INSERT INTO clientes ("
                    + "cpf,"
                    + "tipoUsuario,"
                    + "nome,"  
                    + "dataNasc,"
                    + "sexo,"
                    + "endereco,"
                    + "cidade,"
                    + "estado"
                + ") VALUES ("
                    + "'" + novoCliente.getCpf() + "',"
                    + "'" + novoCliente.getTipoUsuario() + "',"
                    + "'" + novoCliente.getNome() + "',"   
                    + "'" + novoCliente.getdataNasc() + "',"
                    + "'" + novoCliente.getSexo() + "',"
                    + "'" + novoCliente.getEndereco() + "',"
                    + "'" + novoCliente.getCidade() + "',"
                    + "'" + novoCliente.getEstado() + "'"
                + ");");
            
        } catch (Exception e) {
            
            System.out.println("Erro ao cadastrar cliente " +  e.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar cliente");
            this.conectar.fechaBanco();
            
        } finally{            
            this.conectar.fechaBanco();
            JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso");
            //novoCliente.limpaCliente();
            //limparCamposCadastro();
        }                
    }
    
    private void buscaCliente(Cliente novoCliente) {
        this.conectar.conectaBanco();
        
        String consultaId = this.consultaID.getText();
        
         try {
            this.conectar.executarSQL(
                   "SELECT "
                    + "cpf,"     
                    + "tipoUsuario," 
                    + "nome,"
                    + "dataNasc,"       
                    + "sexo,"
                    + "endereco,"
                    + "cidade,"
                    + "estado"
                 + " FROM"
                     + " Clientes"
                 + " WHERE"
                     + " Clientes.id = '" + consultaId + "'"
                + ";"
            );
            
            while(this.conectar.getResultSet().next()){
                novoCliente.setCpf(this.conectar.getResultSet().getString(1));
                novoCliente.setTipoUsuario(this.conectar.getResultSet().getString(2));
                novoCliente.setNome(this.conectar.getResultSet().getString(3));
                novoCliente.setdataNasc(this.conectar.getResultSet().getString(4));
                novoCliente.setSexo(this.conectar.getResultSet().getString(5));
                novoCliente.setEndereco(this.conectar.getResultSet().getString(6));
                novoCliente.setCidade(this.conectar.getResultSet().getString(7));
                novoCliente.setEstado(this.conectar.getResultSet().getString(8));
           }
            
           if(novoCliente.getNome() == ""){
                JOptionPane.showMessageDialog(null, "Cliente não encontrado!");
           }
           
        } catch (Exception e) {            
            System.out.println("Erro ao consultar cliente " +  e.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao buscar cliente");
            
        }finally{
            consultaCPF.setText(novoCliente.getCpf());
            consultaUsuario.setText(novoCliente.getTipoUsuario());
            consultaNome.setText(novoCliente.getNome());
            consultaDataNasc.setText(novoCliente.getdataNasc());
            consultaSexo.setSelectedItem(novoCliente.getSexo());
            consultaEndereco.setText(novoCliente.getEndereco());
            consultaCidade.setText(novoCliente.getCidade());
            consultaEstado.setSelectedItem(novoCliente.getEstado());
            this.conectar.fechaBanco();   
        }               
    }
    
    public void atualizarCliente(Cliente novoCliente){

         this.conectar.conectaBanco();

         String consultaID = this.consultaID.getText();

         try {
            this.conectar.updateSQL(
                "UPDATE Clientes SET "
                    + "cpf = '" + consultaCPF.getText() + "',"
                    + "tipoUsuario = '" + consultaUsuario.getText() + "',"
                    + "nome = '" + consultaNome.getText() + "',"
                    + "dataNasc = '" + consultaDataNasc.getText() + "',"
                    + "sexo = '" + consultaSexo.getSelectedItem() + "',"
                    + "endereco = '" + consultaEndereco.getText() + "',"
                    + "cidade = '" + consultaCidade.getText() + "',"
                    + "estado = '" + consultaEstado.getSelectedItem() + "'"
                + " WHERE "
                    + "id = '" + consultaID + "'"
                + ";"
            );


     }catch(Exception e){
            System.out.println("Erro ao atualizar cliente " +  e.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao atualizar cliente");
     }finally{
            this.conectar.fechaBanco();
            novoCliente.limpaCliente();
            limparCamposCadastroCliente();
            JOptionPane.showMessageDialog(null, "Cliente atualizado com sucesso");
        }
     }
    
     private void deletarProduto(Produto novoProduto){
        this.conectar.conectaBanco();
        
        String consultaID = this.txtIdProd.getText(); 
        
        try {            
            this.conectar.updateSQL(
                "DELETE FROM produtos "
                + " WHERE "
                    + "produtos.id = '" + consultaID + "'"
                + ";"            
            );
            
        } catch (Exception e) {
            System.out.println("Erro ao deletar produto " +  e.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao deletar produto");
        }finally{
            this.conectar.fechaBanco();
            limparCamposCadastroProduto();
            JOptionPane.showMessageDialog(null, "Produto deletado com sucesso");            
        }     
       
    }
    
    private void cadastraProduto(Produto novoProduto) {
        this.conectar.conectaBanco();
        
        novoProduto.setNome_produto(txtNomeProd.getText());
        novoProduto.setDescricao(txtDesc.getText());
        novoProduto.setPreco(Float.parseFloat((txtPreco.getText())));
        
        try {
            
            this.conectar.insertSQL("INSERT INTO produtos ("
                    + "prod_nome,"
                    + "descricao,"
                    + "preco"  
                + ") VALUES ("
                    + "'" + novoProduto.getNome_produto() + "',"
                    + "'" + novoProduto.getDescricao() + "',"
                    + "'" + novoProduto.getPreco() + "'"   
                + ");");
            
        } catch (Exception e) {
            
            System.out.println("Erro ao cadastrar produto " +  e.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar produto");
            this.conectar.fechaBanco();
            
        } finally{            
            this.conectar.fechaBanco();
            JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso");
        }                
    }
    
    private void deletarCliente(Cliente novoCliente){
        this.conectar.conectaBanco();
        
        String consultaID = this.consultaID.getText(); 
        
        try {            
            this.conectar.updateSQL(
                "DELETE FROM clientes "
                + " WHERE "
                    + "clientes.id = '" + consultaID + "'"
                + ";"            
            );
            
        } catch (Exception e) {
            System.out.println("Erro ao deletar cliente " +  e.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao deletar cliente");
        }finally{
            this.conectar.fechaBanco();
            limparCamposCadastroCliente();
            JOptionPane.showMessageDialog(null, "Cliente deletado com sucesso");            
        }     
        
    }
    
    private void buscaProduto(Produto novoProduto) {
        
        this.conectar.conectaBanco();
        
        String consultaProduto = this.txtIdProd.getText();
        
        try {
            this.conectar.executarSQL(
                "SELECT "
                    + "prod_nome,"
                    + "descricao,"
                    + "preco"
                 + " FROM"
                     + " produtos"
                 + " WHERE "
                     + " produtos.id = '" + consultaProduto + "'"
                + ";"    
            );
            
            while(this.conectar.getResultSet().next()){               
                novoProduto.setNome_produto(this.conectar.getResultSet().getString(1));
                novoProduto.setDescricao(this.conectar.getResultSet().getString(2));
                novoProduto.setPreco(this.conectar.getResultSet().getFloat(3));
           }
            
            if(novoProduto.getNome_produto() == ""){
                JOptionPane.showMessageDialog(null, "Produto não encontrado!");
            }
            
        } catch (Exception e) {            
            System.out.println("Erro ao consultar produto " +  e.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao buscar produto");
            
        }finally{
            consultaProd.setText(novoProduto.getNome_produto());
            consultaDesc.setText(novoProduto.getDescricao());
            consultaPreco.setText(Float.toString(novoProduto.getPreco()));

            this.conectar.fechaBanco();   
        }    
    }
    
    public void atualizaProduto(Produto novoProduto){
        this.conectar.conectaBanco();
        
        String consultaProduto = this.txtIdProd.getText();
        
        try {
            this.conectar.updateSQL(
                "UPDATE produtos SET "                    
                    + "prod_nome = '" + consultaProd.getText() + "',"
                    + "descricao = '" + consultaDesc.getText() + "',"
                    + "preco = '" + consultaPreco.getText() + "'"                   
                + " WHERE "
                + " id = '" + consultaProduto + "'"
                + ";"
            );
        }catch(Exception e){
            System.out.println("Erro ao atualizar Produto " +  e.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao atualizar Produto");
        }finally{
             consultaProd.setText(novoProduto.getNome_produto());
            consultaDesc.setText(novoProduto.getDescricao());
            consultaPreco.setText(Float.toString(novoProduto.getPreco()));
           
            JOptionPane.showMessageDialog(null, "Produto atualizado com sucesso");
        }
    }
    
    private void limparCamposCadastroCliente(){
        txtNome.setText("");
        txtCpf.setText("");
        txtSexo.setSelectedIndex(0);
        txtTipoUsuario.setSelectedIndex(0);
        txtDataNasc.setText("");
        txtEndereco.setText("");
        txtCidade.setText("");
        txtEstado.setSelectedIndex(0);
        consultaID.setText("");
        consultaNome.setText("");
        consultaCPF.setText("");
        consultaDataNasc.setText("");
        consultaSexo.setSelectedIndex(0);
        consultaUsuario.setText("");
        consultaEndereco.setText("");
        consultaCidade.setText("");
        consultaEstado.setSelectedIndex(0);
    }
    
    private void limparCamposCadastroProduto(){
        txtNomeProd.setText("");
        txtDesc.setText("");
        txtPreco.setText("");
        txtIdProd.setText("");
        consultaProd.setText("");
        consultaDesc.setText("");
        consultaPreco.setText("");
    }
    
    private float addValor(float valor) {
        return valor;
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        btnVoltar = new javax.swing.JButton();
        btnCadProd = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        btnCompra = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jButton12 = new javax.swing.JButton();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        btnCreme = new javax.swing.JButton();
        btnNescafe = new javax.swing.JButton();
        btnChantily = new javax.swing.JButton();
        btnBolo = new javax.swing.JButton();
        btnPao = new javax.swing.JButton();
        btnDonnuts = new javax.swing.JButton();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        txtCarrinho = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jButton10 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        btnSite = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        btnCadastrarProd = new javax.swing.JButton();
        txtNomeProd = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtPreco = new javax.swing.JFormattedTextField();
        jLabel19 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDesc = new javax.swing.JTextArea();
        jLabel22 = new javax.swing.JLabel();
        btnLimparProd = new javax.swing.JButton();
        jLabel60 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        btnLimpaConsulta = new javax.swing.JButton();
        consultaProd = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        consultaPreco = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        txtIdProd = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        consultaDesc = new javax.swing.JTextArea();
        btnConsultarProd = new javax.swing.JButton();
        btnAtualizarProd = new javax.swing.JButton();
        btnDeletarProd = new javax.swing.JButton();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        txtDataNasc = new javax.swing.JFormattedTextField();
        txtTipoUsuario = new javax.swing.JComboBox<>();
        txtCpf = new javax.swing.JFormattedTextField();
        jLabel34 = new javax.swing.JLabel();
        btnCadastrar = new javax.swing.JButton();
        jLabel35 = new javax.swing.JLabel();
        CriaSenha = new javax.swing.JPasswordField();
        ConfirmaSenha = new javax.swing.JPasswordField();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtSexo = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtEndereco = new javax.swing.JTextArea();
        txtCidade = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        txtEstado = new javax.swing.JComboBox<>();
        btnLimpar = new javax.swing.JButton();
        jLabel33 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        consultaNome = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        consultaID = new javax.swing.JTextField();
        jLabel53 = new javax.swing.JLabel();
        consultaDataNasc = new javax.swing.JFormattedTextField();
        jLabel55 = new javax.swing.JLabel();
        consultaSexo = new javax.swing.JComboBox<>();
        jLabel54 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        consultaEndereco = new javax.swing.JTextArea();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        consultaCidade = new javax.swing.JTextField();
        consultaEstado = new javax.swing.JComboBox<>();
        btnConsultaCliente = new javax.swing.JButton();
        btnConsultaLimpar = new javax.swing.JButton();
        btnDeletarCliente = new javax.swing.JButton();
        btnAtualizar = new javax.swing.JButton();
        consultaUsuario = new javax.swing.JTextField();
        jLabel58 = new javax.swing.JLabel();
        consultaCPF = new javax.swing.JFormattedTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        jLabel5.setText("Cappucino 190ML...........................................09,50$");

        jLabel6.setText("Café Expresso 90ML.......................................03,50$");

        jButton6.setText("Adicionar ao carrinho");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Menu Star Coffee");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(153, 204, 255));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/fast-food.png"))); // NOI18N
        jButton1.setText("Cardápio");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/signup.png"))); // NOI18N
        jButton3.setText("Cadastro Clientes");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/help.png"))); // NOI18N
        jButton4.setText("Ajuda");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/exit.png"))); // NOI18N
        jButton11.setText("Sair");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        btnVoltar.setBackground(new java.awt.Color(134, 219, 193));
        btnVoltar.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        btnVoltar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/back.png"))); // NOI18N
        btnVoltar.setText("Voltar");
        btnVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVoltarActionPerformed(evt);
            }
        });

        btnCadProd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/produto.png"))); // NOI18N
        btnCadProd.setText("Cadastro Produtos");
        btnCadProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadProdActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnVoltar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButton11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCadProd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                .addComponent(btnCadProd, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63)
                .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(-5, -2, 200, 720));

        jPanel2.setBackground(new java.awt.Color(153, 168, 237));

        jLabel8.setFont(new java.awt.Font("Lucida Sans Typewriter", 1, 72)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 102, 102));
        jLabel8.setText("Powered By");

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Logo-ajaks-bigger.png"))); // NOI18N

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/star-coffee.png"))); // NOI18N

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cafe.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(jLabel1)
                .addGap(108, 108, 108)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 447, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 475, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(41, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addGap(231, 231, 231))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(108, 108, 108))))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addGap(32, 32, 32)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1113, 1113, 1113)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab1", jPanel2);

        jPanel4.setBackground(new java.awt.Color(204, 255, 255));

        btnCompra.setBackground(new java.awt.Color(102, 255, 51));
        btnCompra.setText("Finalizar Compra");
        btnCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCompraActionPerformed(evt);
            }
        });

        jPanel7.setBackground(new java.awt.Color(0, 153, 153));

        jButton12.setBackground(new java.awt.Color(102, 255, 51));
        jButton12.setText("Finalizar Pedido");

        jLabel43.setText("jLabel2");

        jLabel44.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/starcoffee1.png"))); // NOI18N

        jLabel45.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/starcoffee2.png"))); // NOI18N

        jLabel46.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/starcoffee3.png"))); // NOI18N

        jLabel47.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/starcoffee4.png"))); // NOI18N

        jLabel48.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/starcoffee5.png"))); // NOI18N

        jLabel49.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/starcoffee6.png"))); // NOI18N

        btnCreme.setBackground(new java.awt.Color(0, 102, 204));
        btnCreme.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/plus.png"))); // NOI18N
        btnCreme.setText("Adicionar ao carrinho");
        btnCreme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCremeActionPerformed(evt);
            }
        });

        btnNescafe.setBackground(new java.awt.Color(0, 102, 204));
        btnNescafe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/plus.png"))); // NOI18N
        btnNescafe.setText("Adicionar ao carrinho");
        btnNescafe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNescafeActionPerformed(evt);
            }
        });

        btnChantily.setBackground(new java.awt.Color(0, 102, 204));
        btnChantily.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/plus.png"))); // NOI18N
        btnChantily.setText("Adicionar ao carrinho");
        btnChantily.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChantilyActionPerformed(evt);
            }
        });

        btnBolo.setBackground(new java.awt.Color(0, 102, 204));
        btnBolo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/plus.png"))); // NOI18N
        btnBolo.setText("Adicionar ao carrinho");
        btnBolo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBoloActionPerformed(evt);
            }
        });

        btnPao.setBackground(new java.awt.Color(0, 102, 204));
        btnPao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/plus.png"))); // NOI18N
        btnPao.setText("Adicionar ao carrinho");
        btnPao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPaoActionPerformed(evt);
            }
        });

        btnDonnuts.setBackground(new java.awt.Color(0, 102, 204));
        btnDonnuts.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/plus.png"))); // NOI18N
        btnDonnuts.setText("Adicionar ao carrinho");
        btnDonnuts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDonnutsActionPerformed(evt);
            }
        });

        jLabel50.setText("Café com creme e chocolate");

        jLabel51.setText("R$ 18,00");

        jLabel52.setText("Nescafé");

        jLabel61.setText("R$ 10,00");

        jLabel62.setText("Café com chantilly");

        jLabel63.setText("R$ 15,00");

        jLabel64.setText("Bolo de chocolate napolitano");

        jLabel65.setText("R$ 34,00");

        jLabel66.setText("Pães recheados");

        jLabel67.setText("R$ 4,50");

        jLabel68.setText("Donnuts");

        jLabel69.setText("R$ 9,80");

        jLabel70.setText("Carrinho");

        txtCarrinho.setEditable(false);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(423, 423, 423)
                .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(58, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(jLabel65)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel67)
                        .addGap(174, 174, 174)
                        .addComponent(jLabel69)
                        .addGap(68, 68, 68))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel47)
                                        .addGroup(jPanel7Layout.createSequentialGroup()
                                            .addComponent(jLabel64)
                                            .addGap(13, 13, 13))
                                        .addComponent(txtCarrinho, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addGap(12, 12, 12)
                                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel50)
                                            .addComponent(btnCreme)
                                            .addComponent(btnBolo)))
                                    .addComponent(jLabel44))
                                .addGap(34, 34, 34))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel7Layout.createSequentialGroup()
                                .addGap(69, 69, 69)
                                .addComponent(jLabel51))
                            .addComponent(jLabel70, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel48)
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addGap(68, 68, 68)
                                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel61)))
                                    .addComponent(jLabel46)))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(54, 54, 54)
                                .addComponent(jLabel66, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnPao)
                                    .addComponent(btnNescafe))
                                .addGap(18, 18, 18)))
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(72, 72, 72)
                                .addComponent(jLabel62, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(96, 96, 96)
                                .addComponent(jLabel63))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(99, 99, 99)
                                .addComponent(jLabel68, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addComponent(jLabel49))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                    .addComponent(jLabel45)
                                    .addGap(7, 7, 7))
                                .addComponent(btnDonnuts, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(btnChantily, javax.swing.GroupLayout.Alignment.TRAILING))))
                    .addComponent(jLabel43, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(98, 98, 98))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel44)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel49, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel48, javax.swing.GroupLayout.Alignment.TRAILING)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel52)
                            .addComponent(jLabel50))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel61)
                            .addComponent(jLabel51)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel62)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel63)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNescafe, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnChantily, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCreme, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel47)
                            .addComponent(jLabel46))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel64)
                            .addComponent(jLabel66))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel65)
                            .addComponent(jLabel67))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnBolo, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnPao, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel68)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel69)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnDonnuts, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(31, 31, 31)
                .addComponent(jLabel70)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCarrinho, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3300, 3300, 3300)
                .addComponent(jLabel43)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(103, 103, 103))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(300, 300, 300)
                .addComponent(btnCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(345, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(644, Short.MAX_VALUE)
                .addComponent(btnCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 720, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("tab2", jPanel4);

        jPanel6.setBackground(new java.awt.Color(204, 255, 255));

        jLabel4.setText("Codigo do Produto");

        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        jButton10.setBackground(new java.awt.Color(0, 255, 255));
        jButton10.setText("Adicionar");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(316, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(274, 274, 274))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(jButton10)
                        .addGap(326, 326, 326))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(241, 241, 241)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton10)
                .addContainerGap(405, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab4", jPanel6);

        jPanel5.setBackground(new java.awt.Color(204, 191, 216));

        btnSite.setBackground(new java.awt.Color(162, 221, 191));
        btnSite.setFont(new java.awt.Font("Rockwell", 0, 18)); // NOI18N
        btnSite.setText("Acesse nosso site");
        btnSite.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnSite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSiteActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Franklin Gothic Medium Cond", 0, 36)); // NOI18N
        jLabel18.setText("Para mais informações sobre nossos serviços");

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel25.setText("Muito obrigado por utilizar nosso sistema. Ajude-nos reportando os bugs!");

        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/like.png"))); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(83, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 622, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(60, 60, 60))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel25, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(244, 244, 244)
                                .addComponent(jLabel26)
                                .addGap(195, 195, 195)))
                        .addGap(88, 88, 88))))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(272, 272, 272)
                .addComponent(btnSite, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(83, 83, 83)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(93, 93, 93)
                .addComponent(btnSite, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 117, Short.MAX_VALUE)
                .addComponent(jLabel25)
                .addGap(50, 50, 50)
                .addComponent(jLabel26)
                .addGap(139, 139, 139))
        );

        jTabbedPane1.addTab("tab5", jPanel5);

        jPanel8.setBackground(new java.awt.Color(204, 204, 255));

        jPanel9.setBackground(new java.awt.Color(204, 204, 255));

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel17.setText("Cadastro de Produtos");

        jLabel20.setText("Nome do Produto:");

        btnCadastrarProd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/save.png"))); // NOI18N
        btnCadastrarProd.setText("CADASTRAR");
        btnCadastrarProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadastrarProdActionPerformed(evt);
            }
        });

        jLabel12.setText("Descrição do Produto:");

        jLabel14.setText("Preço:");

        try {
            txtPreco.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##.##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel19.setText("R$");

        txtDesc.setColumns(20);
        txtDesc.setRows(5);
        jScrollPane1.setViewportView(txtDesc);

        btnLimparProd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/broom.png"))); // NOI18N
        btnLimparProd.setText("LIMPAR");
        btnLimparProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparProdActionPerformed(evt);
            }
        });

        jLabel60.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/bolinho.gif"))); // NOI18N

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(txtPreco, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(jLabel19))
                    .addComponent(jLabel22)
                    .addComponent(jLabel14)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                    .addComponent(txtNomeProd)
                    .addComponent(jLabel20)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 108, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel60)
                        .addGap(108, 108, 108))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnLimparProd, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCadastrarProd, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(71, 71, 71))))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addGap(52, 52, 52)
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNomeProd, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel22))
                    .addComponent(jLabel60, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPreco, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19))
                .addGap(42, 42, 42)
                .addComponent(btnCadastrarProd, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnLimparProd, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(368, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Cadastro", jPanel8);

        jPanel10.setBackground(new java.awt.Color(204, 204, 255));

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel23.setText("Consulta de produtos");

        btnLimpaConsulta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/broom.png"))); // NOI18N
        btnLimpaConsulta.setText("LIMPAR");
        btnLimpaConsulta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpaConsultaActionPerformed(evt);
            }
        });

        jLabel27.setText("Nome do Produto:");

        jLabel28.setText("Descrição:");

        jLabel29.setText("Preço");

        jLabel21.setText("ID do Produto");

        consultaDesc.setColumns(20);
        consultaDesc.setRows(5);
        jScrollPane2.setViewportView(consultaDesc);

        btnConsultarProd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/search.png"))); // NOI18N
        btnConsultarProd.setText("CONSULTAR");
        btnConsultarProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarProdActionPerformed(evt);
            }
        });

        btnAtualizarProd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/updated.png"))); // NOI18N
        btnAtualizarProd.setText("ATUALIZAR");
        btnAtualizarProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtualizarProdActionPerformed(evt);
            }
        });

        btnDeletarProd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete.png"))); // NOI18N
        btnDeletarProd.setText("DELETAR");
        btnDeletarProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeletarProdActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel29)
                            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel28, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(consultaProd, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(consultaPreco, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel27, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtIdProd, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 265, Short.MAX_VALUE)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnLimpaConsulta, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                            .addComponent(btnConsultarProd, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                            .addComponent(btnAtualizarProd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnDeletarProd, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(63, 63, 63))))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnConsultarProd, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnAtualizarProd, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnDeletarProd, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jLabel23)
                        .addGap(43, 43, 43)
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtIdProd, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(jLabel27)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(consultaProd, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jLabel28)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(jLabel29)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(consultaPreco, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 72, Short.MAX_VALUE)))
                .addComponent(btnLimpaConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(91, 91, 91))
        );

        jTabbedPane2.addTab("Consulta", jPanel10);

        jTabbedPane1.addTab("tab6", jTabbedPane2);

        jPanel11.setBackground(new java.awt.Color(204, 255, 255));

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel24.setText("Nome completo:");

        jLabel30.setText("CPF:");

        jLabel31.setText("Data de nascimento:");

        jLabel32.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel32.setText("Preencha seus dados: ");

        try {
            txtDataNasc.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("####-##-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtDataNasc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDataNascActionPerformed(evt);
            }
        });

        txtTipoUsuario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "Cliente", "Funcionário" }));

        try {
            txtCpf.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel34.setText("Endereço:");

        btnCadastrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/save.png"))); // NOI18N
        btnCadastrar.setText("CADASTRAR");
        btnCadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadastrarActionPerformed(evt);
            }
        });

        jLabel35.setText("Selecione Cliente ou Funcionário");

        jLabel36.setText("Confirmar senha:");

        jLabel37.setText("Criar senha:");

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel16.setText("Cadastro de cliente");

        txtSexo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione", "Masculino", "Feminino" }));

        jLabel15.setText("Sexo");

        jLabel41.setText("Cidade");

        txtEndereco.setColumns(20);
        txtEndereco.setRows(5);
        jScrollPane3.setViewportView(txtEndereco);

        jLabel42.setText("Estado");

        txtEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione", "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO" }));

        btnLimpar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/broom.png"))); // NOI18N
        btnLimpar.setText("LIMPAR");
        btnLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparActionPerformed(evt);
            }
        });

        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Powered_By3.png"))); // NOI18N

        jLabel59.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Logo_AJAKS3.png"))); // NOI18N

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel24)
                            .addComponent(txtTipoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel35)
                            .addComponent(txtNome))
                        .addGap(80, 80, 80))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel32)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel30)
                                    .addComponent(txtCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(40, 40, 40)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel31)
                                    .addComponent(txtDataNasc, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel34)
                                    .addGroup(jPanel11Layout.createSequentialGroup()
                                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel41)
                                            .addComponent(txtCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(25, 25, 25)
                                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel42)
                                            .addComponent(txtEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(0, 14, Short.MAX_VALUE))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel15))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel33)
                            .addComponent(jLabel59))
                        .addGap(40, 40, 40))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnCadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel36, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel37, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(ConfirmaSenha, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
                                .addComponent(CriaSenha, javax.swing.GroupLayout.Alignment.LEADING)))
                        .addGap(80, 80, 80))))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel32)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel59)
                        .addGap(24, 24, 24)
                        .addComponent(jLabel37)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(CriaSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)
                        .addComponent(jLabel36)
                        .addGap(0, 0, 0)
                        .addComponent(ConfirmaSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(51, 51, 51)
                        .addComponent(btnCadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel35)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTipoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel30)
                            .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDataNasc, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(39, 39, 39)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(jLabel34)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(55, 55, 55)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(jLabel42)
                                .addGap(92, 92, 92))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(jLabel41)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(56, 56, 56))))))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane3.addTab("Cadastro", jPanel3);

        jPanel13.setBackground(new java.awt.Color(204, 255, 255));

        jLabel38.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel38.setText("Nome completo:");

        jLabel39.setText("CPF");

        jLabel40.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel40.setText("Consulta de Cliente");

        consultaNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                consultaNomeActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel2.setText("Insira o ID do cliente para consultar:");

        jLabel53.setText("Data de nascimento:");

        try {
            consultaDataNasc.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("####-##-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel55.setText("Sexo");

        consultaSexo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione", "Masculino", "Feminino" }));
        consultaSexo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                consultaSexoActionPerformed(evt);
            }
        });

        jLabel54.setText("Endereço:");

        consultaEndereco.setColumns(20);
        consultaEndereco.setRows(5);
        jScrollPane5.setViewportView(consultaEndereco);

        jLabel56.setText("Cidade");

        jLabel57.setText("Estado");

        consultaEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione", "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO" }));

        btnConsultaCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/search.png"))); // NOI18N
        btnConsultaCliente.setText("CONSULTAR");
        btnConsultaCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultaClienteActionPerformed(evt);
            }
        });

        btnConsultaLimpar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/broom.png"))); // NOI18N
        btnConsultaLimpar.setText("LIMPAR");
        btnConsultaLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultaLimparActionPerformed(evt);
            }
        });

        btnDeletarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete.png"))); // NOI18N
        btnDeletarCliente.setText("DELETAR");
        btnDeletarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeletarClienteActionPerformed(evt);
            }
        });

        btnAtualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/updated.png"))); // NOI18N
        btnAtualizar.setText("ATUALIZAR");
        btnAtualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtualizarActionPerformed(evt);
            }
        });

        jLabel58.setText("Tipo de Usuário");

        try {
            consultaCPF.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Logo_AJAKS3.png"))); // NOI18N

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Powered_By3.png"))); // NOI18N

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap(36, Short.MAX_VALUE)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel56)
                        .addGap(181, 181, 181)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(consultaEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel57)))
                    .addComponent(consultaCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel13Layout.createSequentialGroup()
                            .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel38)
                                .addGroup(jPanel13Layout.createSequentialGroup()
                                    .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel55)
                                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel54, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(consultaSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(consultaCPF, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(31, 31, 31)
                                    .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(consultaUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel58)
                                        .addComponent(consultaDataNasc, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(jPanel13Layout.createSequentialGroup()
                                    .addGap(161, 161, 161)
                                    .addComponent(jLabel53))
                                .addComponent(jLabel39)
                                .addComponent(consultaNome, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel2)
                                .addComponent(consultaID, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(27, 27, 27))))
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel3))
                        .addGap(38, 38, 38))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnConsultaLimpar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnDeletarCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnConsultaCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnAtualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(69, 69, 69))))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addGap(40, 40, 40)
                        .addComponent(btnConsultaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnAtualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(btnDeletarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnConsultaLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(71, 71, 71))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel40)
                        .addGap(31, 31, 31)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(consultaID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addComponent(jLabel38)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(consultaNome, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel39)
                            .addComponent(jLabel53))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(consultaDataNasc, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(consultaCPF, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel55)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(consultaSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addGap(39, 39, 39)
                                .addComponent(jLabel58)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(consultaUsuario)))
                        .addGap(34, 34, 34)
                        .addComponent(jLabel54)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel57)
                            .addComponent(jLabel56, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(consultaCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(consultaEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(112, 112, 112))))
        );

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane3.addTab("Consulta", jPanel12);

        jTabbedPane1.addTab("tab6", jTabbedPane3);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, -40, 770, 760));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        jTabbedPane1.setSelectedIndex(3);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        jTabbedPane1.setSelectedIndex(5);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        jTabbedPane1.setSelectedIndex(1);

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButton11ActionPerformed

    private void btnVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarActionPerformed
        jTabbedPane1.setSelectedIndex(0);
    }//GEN-LAST:event_btnVoltarActionPerformed

    private void btnSiteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSiteActionPerformed
        try {
            String url="https://ajaks.herokuapp.com/";
            java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
        } catch (IOException ex) {
            Logger.getLogger(TelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnSiteActionPerformed

    private void btnCadastrarProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadastrarProdActionPerformed
        cadastraProduto(novoProduto);
    }//GEN-LAST:event_btnCadastrarProdActionPerformed

    private void btnLimparProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparProdActionPerformed
        limparCamposCadastroProduto();
    }//GEN-LAST:event_btnLimparProdActionPerformed

    private void btnLimpaConsultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpaConsultaActionPerformed
        limparCamposCadastroProduto();
    }//GEN-LAST:event_btnLimpaConsultaActionPerformed

    private void btnCadProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadProdActionPerformed
        jTabbedPane1.setSelectedIndex(4);
    }//GEN-LAST:event_btnCadProdActionPerformed

    private void btnConsultarProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarProdActionPerformed
        buscaProduto(novoProduto);
    }//GEN-LAST:event_btnConsultarProdActionPerformed

    private void btnCadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadastrarActionPerformed
        cadastraCliente(novoCliente);
    }//GEN-LAST:event_btnCadastrarActionPerformed

    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparActionPerformed
        limparCamposCadastroCliente();
    }//GEN-LAST:event_btnLimparActionPerformed

    private void consultaNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_consultaNomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_consultaNomeActionPerformed

    private void consultaSexoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_consultaSexoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_consultaSexoActionPerformed

    private void btnConsultaClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultaClienteActionPerformed
        buscaCliente(novoCliente);
    }//GEN-LAST:event_btnConsultaClienteActionPerformed

    private void btnConsultaLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultaLimparActionPerformed
        limparCamposCadastroCliente();
    }//GEN-LAST:event_btnConsultaLimparActionPerformed

    private void btnDeletarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeletarClienteActionPerformed
        deletarCliente(novoCliente);
    }//GEN-LAST:event_btnDeletarClienteActionPerformed

    private void btnDeletarProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeletarProdActionPerformed
        deletarProduto(novoProduto);
    }//GEN-LAST:event_btnDeletarProdActionPerformed

    private void btnAtualizarProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtualizarProdActionPerformed
        atualizaProduto(novoProduto);
    }//GEN-LAST:event_btnAtualizarProdActionPerformed

    private void btnAtualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtualizarActionPerformed
        atualizarCliente(novoCliente);
    }//GEN-LAST:event_btnAtualizarActionPerformed

    private void txtDataNascActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDataNascActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDataNascActionPerformed

    private void btnCremeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCremeActionPerformed
        valor += 18.00f;
        txtCarrinho.setText("R$ " + String.valueOf(df.format(valor)));
    }//GEN-LAST:event_btnCremeActionPerformed

    private void btnNescafeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNescafeActionPerformed
        valor += 10.00f;
        txtCarrinho.setText("R$ " + String.valueOf(df.format(valor)));
    }//GEN-LAST:event_btnNescafeActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    private void btnChantilyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChantilyActionPerformed
        valor += 15.00f;
        txtCarrinho.setText("R$ " + String.valueOf(df.format(valor)));
    }//GEN-LAST:event_btnChantilyActionPerformed

    private void btnBoloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBoloActionPerformed
        valor += 34.00f;
        txtCarrinho.setText("R$ " + String.valueOf(df.format(valor)));
    }//GEN-LAST:event_btnBoloActionPerformed

    private void btnPaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPaoActionPerformed
        valor += 4.50f;
        txtCarrinho.setText("R$ " + String.valueOf(df.format(valor)));
    }//GEN-LAST:event_btnPaoActionPerformed

    private void btnDonnutsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDonnutsActionPerformed
        valor += 9.80f;
        txtCarrinho.setText("R$ " + String.valueOf(df.format(valor)));
    }//GEN-LAST:event_btnDonnutsActionPerformed

    private void btnCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCompraActionPerformed
        JOptionPane.showMessageDialog(this, "Compra realizada com sucesso!");
        txtCarrinho.setText("");
    }//GEN-LAST:event_btnCompraActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPasswordField ConfirmaSenha;
    private javax.swing.JPasswordField CriaSenha;
    private javax.swing.JButton btnAtualizar;
    private javax.swing.JButton btnAtualizarProd;
    private javax.swing.JButton btnBolo;
    private javax.swing.JButton btnCadProd;
    private javax.swing.JButton btnCadastrar;
    private javax.swing.JButton btnCadastrarProd;
    private javax.swing.JButton btnChantily;
    private javax.swing.JButton btnCompra;
    private javax.swing.JButton btnConsultaCliente;
    private javax.swing.JButton btnConsultaLimpar;
    private javax.swing.JButton btnConsultarProd;
    private javax.swing.JButton btnCreme;
    private javax.swing.JButton btnDeletarCliente;
    private javax.swing.JButton btnDeletarProd;
    private javax.swing.JButton btnDonnuts;
    private javax.swing.JButton btnLimpaConsulta;
    private javax.swing.JButton btnLimpar;
    private javax.swing.JButton btnLimparProd;
    private javax.swing.JButton btnNescafe;
    private javax.swing.JButton btnPao;
    private javax.swing.JButton btnSite;
    private javax.swing.JButton btnVoltar;
    private javax.swing.JFormattedTextField consultaCPF;
    private javax.swing.JTextField consultaCidade;
    private javax.swing.JFormattedTextField consultaDataNasc;
    private javax.swing.JTextArea consultaDesc;
    private javax.swing.JTextArea consultaEndereco;
    private javax.swing.JComboBox<String> consultaEstado;
    private javax.swing.JTextField consultaID;
    private javax.swing.JTextField consultaNome;
    private javax.swing.JTextField consultaPreco;
    private javax.swing.JTextField consultaProd;
    private javax.swing.JComboBox<String> consultaSexo;
    private javax.swing.JTextField consultaUsuario;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField txtCarrinho;
    private javax.swing.JTextField txtCidade;
    private javax.swing.JFormattedTextField txtCpf;
    private javax.swing.JFormattedTextField txtDataNasc;
    private javax.swing.JTextArea txtDesc;
    private javax.swing.JTextArea txtEndereco;
    private javax.swing.JComboBox<String> txtEstado;
    private javax.swing.JTextField txtIdProd;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtNomeProd;
    private javax.swing.JFormattedTextField txtPreco;
    private javax.swing.JComboBox<String> txtSexo;
    private javax.swing.JComboBox<String> txtTipoUsuario;
    // End of variables declaration//GEN-END:variables

    private class consultaCpf {

        public consultaCpf() {
        }
    }
}
