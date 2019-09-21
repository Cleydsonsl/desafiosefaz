package com.dsefaz.desafiosefaz;
  
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
  
import com.dsefaz.desafiosefaz.Usuario;
  
public class Connect {
     Connection con = null;
  
     public Connect() throws SQLException {
  
          try {
                Class.forName("com.mysql.jdbc.Driver");
                System.out.println("Instalou driver");
          } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
          }
  
          String url = "jdbc:mysql://localhost:3306/usuario";
          String user = "root";
          String password = "";
          con = DriverManager.getConnection(url, user, password);
     }
  
     public void closeConnection() throws SQLException {
  
          con.close();
     }
  
     // cadastra no banco um usuario passado como parametro
     public boolean insertUsuario(Usuario usuario) {
  
          Statement st = null;
          ResultSet rs = null;
  
          try {
                st = con.createStatement();
  
                PreparedStatement preparedStatement = con
                .prepareStatement("insert into usuario"
                		+"(id, nome, senha, email, ddd, numero, tipo) values(?,?,?,?,?,?,?)");
                preparedStatement.setInt(1, usuario.getId());
                preparedStatement.setString(2, usuario.getNome());
                preparedStatement.setString(3, usuario.getSenha());
                preparedStatement.setString(4, "" + usuario.getEmail());
                preparedStatement.setString(4, "" + usuario.getDdd());
                preparedStatement.setString(4, "" + usuario.getNumero());
                preparedStatement.setString(4, "" + usuario.getTipo());
                
                preparedStatement.execute();
                return true;
          } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(Connect.class.getName());
                lgr.log(Level.SEVERE, ex.getMessage(), ex);
                return false;
  
          }
     }
      
     //lista todos os usuarios cadastrados no banco de dados
     public List<Usuario> listUsuarios() {
  
          ArrayList<Usuario> lista = new ArrayList<Usuario>();
  
          Statement st = null;
          ResultSet rs = null;
  
          try {
                st = con.createStatement();
                String sql = "select * from usuario ";
               rs = st.executeQuery(sql);
  
                while (rs.next()) {
  
                     Usuario usuario = new Usuario();
                     usuario.setId(rs.getInt(1));
                     usuario.setNome(rs.getString(2));
                     usuario.setSenha(rs.getString(3));
                     usuario.setEmail(rs.getString(4));
                     usuario.setDdd(rs.getInt(5));
                     usuario.setNumero(rs.getString(6));
                     usuario.setTipo(rs.getString(7));
                     lista.add(usuario);
                }
  
          } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(Connect.class.getName());
                lgr.log(Level.SEVERE, ex.getMessage(), ex);
  
          } finally {
                try {
                     if (rs != null) {
                          rs.close();
                     }
                     if (st != null) {
                          st.close();
                     }
                     if (con != null) {
                          con.close();
                     }
  
                } catch (SQLException ex) {
                     Logger lgr = Logger.getLogger(Connect.class.getName());
                     lgr.log(Level.WARNING, ex.getMessage(), ex);
                }
          }
          return lista;
     }
}