package jnotepad;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
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
 * Simple text editor
 * @author Gustavo Simon
 */
public class jTela extends JFrame {

    JTextArea jText;
    JLabel characters;
    JLabel modified;
    String previousText = "";
            
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
        jText = new JTextArea();
        jText.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        
        jText.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                updateFooter();
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
                    jText.setText(readArquivo(file.getSelectedFile()));
                    previousText = jText.getText();
                }
                updateFooter();
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
                        writeArquivo(file.getSelectedFile().getPath(), jText.getText());
                    } catch (IOException ex) {
                        Logger.getLogger(jTela.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                updateFooter();                
            }
        });
// Cria o listener para a opção de sair do programa        
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
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
        characters = new JLabel("Caracteres: 0");
        modified = new JLabel("Modificado: não");
        Container ct = getContentPane();
        JPanel center = new JPanel();
        ct.add(jText, BorderLayout.CENTER);
        JPanel footer = new JPanel();
        footer.setLayout(new FlowLayout(FlowLayout.LEFT));
        footer.add(characters);
        footer.add(modified);
        ct.add(footer, BorderLayout.SOUTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
// Le o arquivo e retorna o conteúdo    
    public String readArquivo(File arquivo) {
        BufferedReader buffRead = null;
        try {
            buffRead = new BufferedReader(new FileReader(arquivo.getPath()));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(jTela.class.getName()).log(Level.SEVERE, null, ex);
        }
        String line = "", text = "";
        while (true) {
            try {
                line = buffRead.readLine();
                if (line == null) {
                    break;
                }
                if (!"".equals(text)){
                    text += "\n";
                }
                text += line;
            } catch (IOException ex) {
                Logger.getLogger(jTela.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            buffRead.close();
        } catch (IOException ex) {
            Logger.getLogger(jTela.class.getName()).log(Level.SEVERE, null, ex);
        }
        return text;
    }
// Grava o conteúdo digitado em um arquivo    
    public void writeArquivo (String path, String texto) throws IOException{
        FileWriter arquivo = new FileWriter(path);
        PrintWriter gravarArq = new PrintWriter(arquivo);
        gravarArq.printf(texto);
        arquivo.close();
    }
    
    public void updateFooter() {
        characters.setText("Caracteres: " + jText.getText().length());
        if (previousText.equals(jText.getText())) {
            modified.setText("Modificado: não");
        } else {
            modified.setText("Modificado: sim");
        }
    }
}
