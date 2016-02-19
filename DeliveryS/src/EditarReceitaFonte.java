import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class EditarReceitaFonte extends JFrame implements ActionListener{
	
	private Container container;
	private JButton btCadastrar;
	private JButton btLimpar;
	private JButton btNovaCategoria;
	
	private JTextField tfNome;
	private JTextField tfValorTotal;
	private JTextField tfPrioridade;
	private JTextField tfPeriodo;
	private	JTextField tfDataCadastro;
	private	JTextField tfDataAbertura;
	private	JTextField tfDataVencimento;
	
	private String valorCategoria;
	private String string_categoria;
	private String tipoValor;
	private String status_fonte;
    
	private JLabel lNome;
	private JLabel lValorTotal;
	private JLabel lCategoria;
	private JLabel lTipoValor;
	private JLabel lPrioridade;
	private JLabel lPeriodo;
	private	JLabel lDataCadastro;
	private	JLabel lDataAbertura;
	private	JLabel lDataVencimento;
	
	private DefaultTableModel modelo = new DefaultTableModel();
	private int linhaS;
	private String codigo;
	
	private ConnectionFactory con;
	private Connection c;

	//Para a lista de categorias:
	ResultSet rs = null;
	DefaultListModel model = null;
	JList list = null;
	
	private void initConexao(){if(con == null){ con = new ConnectionFactory(); c = con.getConnection();}}
	
	private final String SELECT = "SELECT * FROM Fonte_receita WHERE cod_fonte_r=?";
	private final String UPDATE = "UPDATE Fonte_receita SET nome_fonte_r=?, valor_fonte_r=?, data_cadastro_fonte_r=?, data_abertura_fonte_r=?, data_ultimo_recebimento_fonte_r=?, data_fechamento_fonte_r=?, status_fonte_r=?, prioridade_fonte_r=?, periodo_fonte_r=?, tipo_valor_fonte_r=?, cod_categoria_r=? WHERE cod_fonte_r=?";
	
	Verificacao formatos = new Verificacao();
	
	public EditarReceitaFonte(DefaultTableModel md, String cod, int linha)
	{		
		criaJanelaRecFonte();
		modelo = md;
		codigo = cod;
		initConexao();
		
		 try{
			 PreparedStatement ptStatement = c.prepareStatement(SELECT);
			 ptStatement.setString(1, cod);
			 rs = ptStatement.executeQuery();
			 rs.first();
	         
	         tfNome.setText(rs.getString("nome_fonte_r"));
	         tfValorTotal.setText(rs.getString("valor_fonte_r"));
	         tfPrioridade.setText(rs.getString("prioridade_fonte_r"));
	         tfPeriodo.setText(rs.getString("periodo_fonte_r"));	         
	         tfDataCadastro.setText(rs.getString("data_cadastro_fonte_r"));
	         tfDataAbertura.setText(rs.getString("data_abertura_fonte_r"));
	         tfDataVencimento.setText(rs.getString("data_fechamento_fonte_r"));
	         
	         
		 }catch (SQLException ex) {
	            System.out.println("ERRO: " + ex);
	        } 
		
		
		linhaS = linha;
	}
	
	public void criaJanelaRecFonte()
	{
		btCadastrar = new JButton("Alterar fonte de receita");
		btLimpar = new JButton("Limpar formulário");
		
		tfNome = new JTextField(30);
		tfValorTotal = new JTextField(20);
		tfPrioridade = new JTextField(10);
		tfPeriodo = new JTextField(10);
		tfDataCadastro = new JTextField(10);
		tfDataAbertura = new JTextField(10);
		tfDataVencimento = new JTextField(10);
		
		lNome = new JLabel("Nome da fonte");
		lValorTotal = new JLabel("Valor (R$ 00.00): R$");
		lCategoria = new JLabel("Categoria:");
		lTipoValor = new JLabel("Tipo do valor:");
		lPrioridade = new JLabel("Prioridade");
		lPeriodo = new JLabel("Período de recebimento da receita (em dias)");
		lDataCadastro = new JLabel("Data do cadastro (AAAA/MM/DD)");
		lDataAbertura = new JLabel("Data de início do recebimento da receita (AAAA/MM/DD) (data do recebimento será prevista com base na contagem de dias do período a partir dessa data)");
		lDataVencimento = new JLabel("Data de fim do recebimento da receita (AAAA/MM/DD) (opcional)");
		
		container = this.getContentPane();
		
		container.add(lNome);
		container.add(tfNome);
		container.add(lValorTotal);
		container.add(tfValorTotal);
		
		container.add(lDataCadastro);
		container.add(tfDataCadastro); 

		container.add(lDataAbertura);
		container.add(tfDataAbertura);
		
		container.add(lDataVencimento);
		container.add(tfDataVencimento); 
		
		container.add(lPeriodo);
		container.add(tfPeriodo);
		
		container.add(lTipoValor);
        //Listar fixo e variável
        final JComboBox comboValor = new JComboBox(); 
        comboValor.addItem("Fixo");
        comboValor.addItem("Varia a cada período");
        this.getContentPane().add(comboValor);  
	    this.pack();  
	    this.setVisible(true);
	    //Selecionar o tipo do valor
	    ActionListener actionListenerValor = new ActionListener() {
	       public void actionPerformed(ActionEvent actionEvent) {
	           tipoValor = (String)comboValor.getSelectedItem();
	        }
	    };	              
	    comboValor.addActionListener(actionListenerValor);  
	    
		container.add(lPrioridade);
		container.add(tfPrioridade);
		container.add(lCategoria);
		
		initConexao();
		final JComboBox combo = new JComboBox(); 		
        boolean status = false;
        
        //Listar as categorias
        try {
	        	Statement lista = c.createStatement();
	        	String select = "SELECT * FROM Categoria_receita";
	            PreparedStatement ptStatement = c.prepareStatement(select);
	            rs = ptStatement.executeQuery();
	            ResultSetMetaData rsmd = rs.getMetaData();	
	            String[] string =  new String[rsmd.getColumnCount()];	
	    	    while (rs.next())  {
	    	    	String itemCode = rs.getString("nome_categoria_r");
	    	    	combo.addItem(itemCode);
	    	    }	
	    	    status = true;
	    	    this.getContentPane().add(combo);  
	            this.pack();  
	            this.setVisible(true);
	            
	            //Selecionar a categoria
	            ActionListener actionListener = new ActionListener() {
	                public void actionPerformed(ActionEvent actionEvent) {
	                  valorCategoria = (String)combo.getSelectedItem();
	                }
	              };	              
	              combo.addActionListener(actionListener);            
        } catch (SQLException ex) {
            System.out.println("ERRO: " + ex);
        } 
       
        //Nova categoria
        btNovaCategoria = new JButton("Cadastrar nova categoria");
        btNovaCategoria.addActionListener(
        	new ActionListener(){
        	    public void actionPerformed(ActionEvent e){        	    	
        	    	String categoria;        	    	
        	    	categoria = JOptionPane.showInputDialog("Nova Categoria");        	    	
        	    	initConexao();
        	        boolean status = false;
        	        try {        		        	
	        	        	Statement teste = c.createStatement();
	        	            String query = "INSERT INTO Categoria_receita (nome_categoria_r) VALUES (?)";
	        	            PreparedStatement pStatement = c.prepareStatement(query);	        	          
	        	            pStatement.setString(1, categoria);
	        	            pStatement.executeUpdate();	        	            
	        	            String select = "SELECT * FROM Categoria_receita";
	                        PreparedStatement ptStatement = c.prepareStatement(select);
	                        rs = ptStatement.executeQuery();
	        	            status = true;
        	        } catch (SQLException ex) {
        	            System.out.println("ERRO: " + ex);
        	        }
        	        combo.addItem(categoria);        	        
        	    }
        	}
        );
        container.add(btNovaCategoria);       
               
		container.add(btCadastrar);         
        container.add(btLimpar);
           
        btLimpar.addActionListener(this);
        btCadastrar.addActionListener(this);
		this.setLayout(new FlowLayout()); 
		//this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setVisible(true);
		this.setTitle("Receita - Fonte"); 
		this.setSize(1000, 300); 
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btLimpar)
		{	        
	        limparRecFonte();
		}
		
		else if (e.getSource()==btCadastrar)
		{
			//ATENÇÃO: não há tratamento pra formato de valor e data
			if(valorCategoria==null){
				JOptionPane.showMessageDialog(this, "Selecione uma categoria!");
			}	
			else if(tipoValor==null){
				JOptionPane.showMessageDialog(this, "Selecione uma tipo para o valor (fixo ou variável)!");
			}	
			else if((tfNome.getText()).equals("")){
				JOptionPane.showMessageDialog(this, "Defina um nome para a fonte!");
			}
			else if((tfValorTotal.getText()).equals("")){
				JOptionPane.showMessageDialog(this, "Defina um valor para a fonte!");
			}
			else if((tfDataCadastro.getText()).equals("")){
				JOptionPane.showMessageDialog(this, "Coloque a data do cadastro da fonte!");
			}
			else if((tfDataAbertura.getText()).equals("")){
				JOptionPane.showMessageDialog(this, "Coloque a data de início do recebimento da fonte!");
			}
			else if((tfPrioridade.getText()).equals("")){
				JOptionPane.showMessageDialog(this, "Defina a prioridade da fonte!");
			}
			else if((tfPeriodo.getText()).equals("")){
				JOptionPane.showMessageDialog(this, "Defina o período de recebimento da fonte!");
			}
			
			else if(!formatos.isFloat(tfValorTotal.getText())){
				JOptionPane.showMessageDialog(this, "Defina um valor real e positivo para o item!");
			}
			
			else{			
				initConexao();
		        boolean status = false;
	            try {
	            	String select = "SELECT * FROM Categoria_receita";
                    PreparedStatement ptStatement = c.prepareStatement(select);
                    rs = ptStatement.executeQuery();
	            	rs.first();
	                    do{
	                        if(rs.getString("nome_categoria_r").equals(valorCategoria)){
	                            string_categoria = rs.getString("cod_categoria_r");
	                        }
	                    }while(rs.next());
	            	} catch (SQLException e1) {
	                    e1.printStackTrace();
	            }
	            System.out.println("Categoria valor: " + string_categoria);
	            
		        try {        		  
			        	Statement teste = c.createStatement();
			            PreparedStatement pStatement = c.prepareStatement(UPDATE);
			            
			            float i = Float.parseFloat(tfValorTotal.getText());
			            int categoria = Integer.parseInt(string_categoria);
			            String j;
			            if((tfDataVencimento.getText()).equals("") || tfDataVencimento.getText().equals("0000/00/00")){
			            	tfDataVencimento.setText("0000/00/00");
			            	j = "0";
			            	status_fonte = "Receita Não Finalizada";
			            }
			            else{
			            	j = "1";
			            	status_fonte = "Receita Finalizada";
			            }
			            String k;
			            if(tipoValor.equals("Fixo")){
			            	k = "0";
			            }
			            else{
			            	k = "1";
			            }
			            pStatement.setString(1, tfNome.getText());
			            pStatement.setDouble(2, i);
			            pStatement.setString(3, tfDataCadastro.getText());
			            pStatement.setString(4, tfDataAbertura.getText());
			            pStatement.setString(5, tfDataAbertura.getText());
			            pStatement.setString(6, tfDataVencimento.getText());
			            pStatement.setString(7, j);
			            pStatement.setString(8, tfPrioridade.getText());
			            pStatement.setString(9, tfPeriodo.getText());
			            pStatement.setString(10, k);
			            pStatement.setInt(11, categoria);
			            pStatement.setString(12, codigo);
			             
			            pStatement.executeUpdate();
			            
			            modelo.removeRow(linhaS);
			            modelo.addRow(new Object[]{codigo,tfNome.getText(),i,valorCategoria,tfDataCadastro.getText(),tfDataAbertura.getText(),tfDataAbertura.getText(),tfDataVencimento.getText(),tfPrioridade.getText(),tfPeriodo.getText(),status_fonte,tipoValor});
			           
			            status = true;
			            JOptionPane.showMessageDialog(this, "Fonte alterada com sucesso!"); 
			            
		        	} catch (SQLException ex) {
		        		System.out.println("ERRO: " + ex);
		        } 			
		
			}
		}
		
		
	}
	
	public void limparRecFonte()
	{
		tfNome.setText("");
	    tfValorTotal.setText("");
	    tfPrioridade.setText("");
	    tfPeriodo.setText("");
	    tfDataCadastro.setText("");
	    tfDataAbertura.setText("");
	    tfDataVencimento.setText("");
	    valorCategoria = null;
		tipoValor = null;
	}
	
}
