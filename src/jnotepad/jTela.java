package jnotepad;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 *
 * @author Gustavo Simon
 */
public class jTela extends JFrame {

    public void montarTela() {
// Indica o título, o tamanho da janela e que deve começar no centro
        setTitle("jNotepad");
        setSize(400, 400);
        setLocationRelativeTo(null);
// Cria a barra de menu
        JMenuBar menu = new JMenuBar();
        setJMenuBar(menu);
// Adiciona os botões na barra de menu
        JMenu fileMenu = new JMenu("Arquivo");
        JMenu editMenu = new JMenu("Ajuda");
        menu.add(fileMenu);
        menu.add(editMenu);
// Adiciona as opções de menu aos botões
        JMenuItem open = new JMenuItem("Abrir");
        JMenuItem save = new JMenuItem("Salvar");
        JMenuItem exit = new JMenuItem("Sair");
        JMenuItem about = new JMenuItem("Sobre");
        fileMenu.add(open);
        fileMenu.add(save);
        fileMenu.add(exit);
        editMenu.add(about);
// Cria a área de texto para digitação
        JTextArea jText = new JTextArea();
        jText.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        
        jText.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                System.out.println(contaCaracteres(jText.getText()));
                
            }
        });        
// Cria o listener para a opção de abrir um arquivo para edição
        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                JFileChooser file = new JFileChooser();
                file.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int i = file.showOpenDialog(null); 
                if (i == JFileChooser.APPROVE_OPTION) {
                    jText.setText(leArquivo(file.getSelectedFile()));
                }
            }
        });
// Cria o listener para a opção de salvar o arquivo
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                JFileChooser file = new JFileChooser();
                file.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int i = file.showSaveDialog(null); 
                if (i == JFileChooser.APPROVE_OPTION) {
                    try {
                        gravaArquivo(file.getSelectedFile().getPath(), jText.getText());
                    } catch (IOException ex) {
                        Logger.getLogger(jTela.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
// Cria o listener para a opção de sair do programa        
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                setVisible(false);
                System.exit(0);
            }
        });
// Cria o listener para a opção de ajuda do programa
        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                JOptionPane.showMessageDialog(null, "JNotepad: editor de texto simples.\nConstruído na disciplina de Programação III.", "Sobre o JNotepad", WIDTH, null);
            }
        });
// Cria um border layout
        setLayout(new BorderLayout());
        JLabel caracteres = new JLabel("Caracteres: ");
        JLabel qtdlinhas = new JLabel();
        qtdlinhas.setText("1");
        JLabel modificado = new JLabel("Modificado: ");
        JLabel modif = new JLabel();
        Container ct = getContentPane();
        JPanel center = new JPanel();
        ct.add(jText, BorderLayout.CENTER);
        JPanel footer = new JPanel();
        footer.setLayout(new BorderLayout());
        footer.add(caracteres, BorderLayout.WEST);
        footer.add(qtdlinhas);
        footer.add(modificado);
        footer.add(modif);
        ct.add(footer, BorderLayout.SOUTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
// Le o arquivo e retorna o conteúdo    
    public String leArquivo(File arquivo) {
        BufferedReader buffRead = null;
        try {
            buffRead = new BufferedReader(new FileReader(arquivo.getPath()));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(jTela.class.getName()).log(Level.SEVERE, null, ex);
        }
        String linha = "", texto = "";
        while (true) {
            try {
                linha = buffRead.readLine();
                if (linha == null) {
                    break;
                }
                if (!"".equals(texto)){
                    texto += "\n";
                }
                texto += linha;
            } catch (IOException ex) {
                Logger.getLogger(jTela.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            buffRead.close();
        } catch (IOException ex) {
            Logger.getLogger(jTela.class.getName()).log(Level.SEVERE, null, ex);
        }
        return texto;
    }
// Grava o conteúdo digitado em um arquivo    
    public void gravaArquivo (String path, String texto) throws IOException{
        FileWriter arquivo = new FileWriter(path);
        PrintWriter gravarArq = new PrintWriter(arquivo);
        gravarArq.printf(texto);
        arquivo.close();
    }
// Conta o numero de caracteres da string recebida por parâmetro
    public int contaCaracteres(String texto) {
        int letras = 0;
        for(int i = 0; i < texto.length(); i++){
            if(isLetter(texto.charAt(i))){
                letras++;
            }
        }    
        return letras;
    }
// Retorna se o caractrete é uma letra
    public boolean isLetter(char ch) {
        if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')) {
            return true;
        } else {
            return false;
        }
    }
}
