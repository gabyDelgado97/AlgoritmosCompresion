package algoritmosdecompresion;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class InterfazGrafica extends JFrame {

    public static void main(String[] args) throws IOException {
        InterfazGrafica ventanaGrande = new InterfazGrafica();
    }
    
    public InterfazGrafica() {
        super("Codificación Aritmética");
        PanelPpal panelCompleto = new PanelPpal();
        JScrollPane panelSPrincipal = new JScrollPane(panelCompleto, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(panelSPrincipal);
        setVisible(true);
        setSize(800, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    class PanelPpal extends JPanel implements ActionListener {
        
        public String mensajeAscii = "";
        protected static final String CODIFICAR = "Codificar";
        protected static final String stringMensaje = "Mensaje a codificar";
        protected static final String stringAscii = "Ascii";
        protected static final String stringAritmetica = "Secuencia binaria de representación: ";
        protected static final String stringFuente = "De caracter a decimal";
        protected static final String stringHuffman = "De caracter a binario";

        JTextArea textMensaje;
        JTextArea textAscii;
        JTextArea textHuffman1;
        JTextArea textHuffman2;
        JTextArea textAritmetica;
        JTextField longitudAscii;
        JTextField longitudHuffman1;
        JTextField longitudHuffman2;
        JTextField longitudAritmetica;
        JTextField ratioAscii;
        JTextField ratioAritmetica;
        JLabel labelLongAscii;
        JLabel labelLongAritmetica;
        JLabel labelRatioAscii;
        JLabel labelRatioAritmetica;
        JLabel labelMensaje;
        JComponent buttonPanel;
        JPanel panelPrincipal;
        JScrollPane panelSPrincipal;
        JPanel mensajePanel;
        JPanel asciiPanel;
        JPanel fuentePanel;
        JPanel huffmanPanel;
        JPanel aritmeticaPanel;
        JPanel aritmeticaTablaPanel;
        JPanel statsPanel;
        
        JTable tblFuente1;
        DefaultTableModel modeloF1;
        JTable tblFuente2;
        DefaultTableModel modeloF2;
        JTable tblHF1;
        DefaultTableModel modeloHF1;
        JTable tblHF2;
        DefaultTableModel modeloHF2;
        JTable tblAritmetica;
        DefaultTableModel modeloArit;

        public PanelPpal() {
            mensajePanel = new JPanel();
            mensajePanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(stringMensaje),BorderFactory.createEmptyBorder(5, 5, 5, 5)));
            textMensaje = new JTextArea(5, 30);
            textMensaje.setLineWrap(true);
            JScrollPane mensajeScrollPane = new JScrollPane(textMensaje);
            mensajeScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            mensajeScrollPane.setPreferredSize(new Dimension(350, 110));
            mensajePanel.add(mensajeScrollPane);

            asciiPanel = new JPanel();
            asciiPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(stringAscii),BorderFactory.createEmptyBorder(5, 5, 5, 5)));
            textAscii = new JTextArea(5, 30);
            textAscii.setLineWrap(true);
            textAscii.setEditable(false);
            JScrollPane asciiScrollPane = new JScrollPane(textAscii);
            asciiScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            asciiScrollPane.setPreferredSize(new Dimension(350, 110));
            asciiPanel.add(asciiScrollPane);

            aritmeticaPanel = new JPanel();
            aritmeticaPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(stringAritmetica),BorderFactory.createEmptyBorder(5, 5, 5, 5)));
            textAritmetica = new JTextArea(5, 30);
            textAritmetica.setLineWrap(true);
            textAritmetica.setEditable(false);
            JScrollPane aritmeticaScrollPane = new JScrollPane(textAritmetica);
            aritmeticaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            aritmeticaScrollPane.setPreferredSize(new Dimension(350, 110));
            aritmeticaPanel.add(aritmeticaScrollPane);

            fuentePanel = new JPanel(new GridLayout(1, 2));
            fuentePanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(stringFuente),BorderFactory.createEmptyBorder(5, 5, 5, 5)));
            modeloF1 = new DefaultTableModel();
            tblFuente1 = new JTable(modeloF1);
            tblFuente1.setGridColor(Color.BLACK);
            JScrollPane scrollFuente1 = new JScrollPane(tblFuente1);
            scrollFuente1.setPreferredSize(new Dimension(10, 10));
            modeloF1.addColumn("Símbolo");
            modeloF1.addColumn("Proporción");

            modeloF2 = new DefaultTableModel();
            tblFuente2 = new JTable(modeloF2);
            tblFuente2.setGridColor(Color.BLACK);
            JScrollPane scrollFuente2 = new JScrollPane(tblFuente2);
            scrollFuente2.setPreferredSize(new Dimension(0,0));

            fuentePanel.add(scrollFuente1);
            fuentePanel.add(scrollFuente2);

            huffmanPanel = new JPanel(new GridLayout(1, 2));
            huffmanPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(stringHuffman),BorderFactory.createEmptyBorder(5, 5, 5, 5)));
            modeloHF1 = new DefaultTableModel();
            tblHF1 = new JTable(modeloHF1);
            tblHF1.setGridColor(Color.BLACK);
            JScrollPane scrollHF1 = new JScrollPane(tblHF1);
            scrollHF1.setPreferredSize(new Dimension(10, 10));
            modeloHF1.addColumn("Símbolo");
            modeloHF1.addColumn("En Binario");

            modeloHF2 = new DefaultTableModel();
            tblHF2 = new JTable(modeloHF2);
            tblHF2.setGridColor(Color.BLACK);
            JScrollPane scrollHF2 = new JScrollPane(tblHF2);
            scrollHF2.setPreferredSize(new Dimension(0, 0));
            

            huffmanPanel.add(scrollHF1);
            huffmanPanel.add(scrollHF2);

            aritmeticaTablaPanel = new JPanel(new GridLayout(1, 2));
            aritmeticaTablaPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Aritmética"),BorderFactory.createEmptyBorder(5, 5, 5, 5)));
            modeloArit = new DefaultTableModel();
            tblAritmetica = new JTable(modeloArit);
            tblAritmetica.setGridColor(Color.BLACK);
            JScrollPane scrollArit = new JScrollPane(tblAritmetica);
            scrollArit.setPreferredSize(new Dimension(10, 10));
            modeloArit.addColumn("Símbolo");
            modeloArit.addColumn("Intervalo");

            aritmeticaTablaPanel.add(scrollArit);

            statsPanel = new JPanel(new GridLayout(2, 2));

            JButton botonCodificar = new JButton(CODIFICAR);
            botonCodificar.setActionCommand(CODIFICAR);
            botonCodificar.addActionListener(this);
            JPanel buttonPanel = new JPanel();
            buttonPanel.add(botonCodificar);
            statsPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Longitudes y Proporciones"),BorderFactory.createEmptyBorder(5, 5, 5, 5)));
            longitudAscii = new JTextField();
            longitudAscii.setEditable(false);
            labelLongAscii = new JLabel("Longitud Ascii: ");
            labelLongAscii.setLabelFor(longitudAscii);
            
            longitudAritmetica = new JTextField();
            longitudAritmetica.setEditable(false);
            labelLongAritmetica = new JLabel("Longitud Aritmetica: ");
            labelLongAritmetica.setLabelFor(longitudAritmetica);

            ratioAscii = new JTextField();
            ratioAscii.setEditable(false);
            labelRatioAscii = new JLabel("Proporción Ascii: ");
            labelRatioAscii.setLabelFor(labelRatioAscii);
            
            ratioAritmetica = new JTextField();
            ratioAritmetica.setEditable(false);
            labelRatioAritmetica = new JLabel("Proporción Aritmetica: ");
            labelRatioAritmetica.setLabelFor(labelRatioAritmetica);

            JPanel textControlsPane1 = new JPanel();
            GridBagLayout gridbag = new GridBagLayout();
            GridBagConstraints c = new GridBagConstraints();
            textControlsPane1.setLayout(gridbag);

            JLabel[] labels = {labelLongAscii, labelLongAritmetica};
            JTextField[] textFields = {longitudAscii, longitudAritmetica};
            addLabelTextRows(labels, textFields, gridbag, textControlsPane1);

            JPanel textControlsPane2 = new JPanel();
            textControlsPane2.setLayout(gridbag);

            JLabel[] labels2 = {labelRatioAscii, labelRatioAritmetica};
            JTextField[] textFields2 = {ratioAscii, ratioAritmetica};
            addLabelTextRows(labels2, textFields2, gridbag, textControlsPane2);

            statsPanel.add(textControlsPane1);
            statsPanel.add(textControlsPane2);
            statsPanel.add(buttonPanel);

            panelPrincipal = new JPanel(new GridLayout(5, 4));
            panelPrincipal.add(mensajePanel);
            panelPrincipal.add(fuentePanel);
            panelPrincipal.add(huffmanPanel);
            panelPrincipal.add(aritmeticaPanel);
            panelPrincipal.add(aritmeticaTablaPanel);
            panelPrincipal.add(asciiPanel);
            panelPrincipal.add(statsPanel);
            add(panelPrincipal);
        }
        
        private void addLabelTextRows(JLabel[] labels,JTextField[] textFields,GridBagLayout gridbag,Container container) {
            GridBagConstraints c = new GridBagConstraints();
            c.anchor = GridBagConstraints.EAST;
            int numLabels = labels.length;
            for (int i = 0; i < numLabels; i++) {
                c.gridwidth = GridBagConstraints.RELATIVE;
                c.fill = GridBagConstraints.NONE;
                c.weightx = 0.0;
                container.add(labels[i], c);
                c.gridwidth = GridBagConstraints.REMAINDER;
                c.fill = GridBagConstraints.HORIZONTAL;
                c.weightx = 1.0;
                container.add(textFields[i], c);
            }
        }
        
        public void actionPerformed(ActionEvent e) {
            if (CODIFICAR.equals(e.getActionCommand())) {
                /* Creo los modelos nuevos para las tablas que tengo creadas */
                DefaultTableModel modeloNF1 = new DefaultTableModel();
                DefaultTableModel modeloNHF1 = new DefaultTableModel();
                DefaultTableModel modeloNHF2 = new DefaultTableModel();
                DefaultTableModel modeloNArit = new DefaultTableModel();
                
                String[] huffMan = new String[256];
                String[][] huffMan2 = new String[256][256];
                int[] fuenteUno = new int[256];
                int[][] fuenteDos = new int[256][256];
                float[] L = new float[256];
                float[] H = new float[256];

                Huffman codificadorJABAJAVL = new Huffman();
                String mensaje = textMensaje.getText();
                StringBuffer fileContents = new StringBuffer(mensaje);
                int longitud = fileContents.length();
                huffMan = codificadorJABAJAVL.processFile(fileContents.toString(), fuenteUno);
                mensajeAscii = codificadorJABAJAVL.crearMensajeAscii(fileContents);
                
                modeloNF1.addColumn("Símbolo");
                modeloNF1.addColumn("Proporción");
                tblFuente1.setModel(modeloNF1);
                Object[] fila = new Object[2];
                for (int i = 0; i < 255; i++) {
                    if (fuenteUno[i] > 0) {
                        fila[0] = (char) i;
                        fila[1] = (float) fuenteUno[i] / longitud;
                        modeloNF1.addRow(fila);
                    }
                }
                
                modeloNHF1.addColumn("Símbolo");
                modeloNHF1.addColumn("En Binario");
                tblHF1.setModel(modeloNHF1);
                Object[] fila3 = new Object[2];
                for (int i = 0; i < 255; i++) {
                    if (fuenteUno[i] > 0) {
                        fila3[0] = (char) i;
                        fila3[1] = huffMan[i];
                        modeloNHF1.addRow(fila3);
                    }
                }

                
                tblHF2.setModel(modeloNHF2);
                Object[] fila4 = new Object[2];
                for (int i = 0; i < 255; i++) {
                    for (int j = 0; j < 255; j++) {
                        if (fuenteDos[i][j] > 0) {
                            fila4[0] = (char) i + "" + (char) j;
                            fila4[1] = huffMan2[i][j];
                            modeloNHF2.addRow(fila4);
                        }
                    }
                }

                modeloNArit.addColumn("Símbolo");
                modeloNArit.addColumn("Intervalo");
                tblAritmetica.setModel(modeloNArit);
                Object[] fila5 = new Object[2];
                int primero = 0;
                int segundo = 0;
                for (int i = 0; i < 255; i++) {
                    if (fuenteUno[i] > 0) {
                        primero = segundo;
                        segundo = segundo + fuenteUno[i];
                        L[i] = (float) primero / longitud;
                        H[i] = (float) segundo / longitud;
                        fila5[0] = (char) i;
                        fila5[1] = "[" + L[i] + " , " + H[i] + " )";
                        modeloNArit.addRow(fila5);
                    }
                }

                // Creo el objeto de codificacion Aritmética para calcular el mensaje.
                Aritmetica codifAritmetica = new Aritmetica();
                String mensajeAritmetica = Aritmetica.crearMensajeAritmetico(fileContents, L, H);
                textAscii.setText(mensajeAscii);
                textAritmetica.setText(mensajeAritmetica);
                /* Escribe las longitudes y los ratios en los TextFields correspondientes. */
                longitudAscii.setText(Integer.toString(mensajeAscii.length()));
                longitudAritmetica.setText(Integer.toString(mensajeAritmetica.length()));
                int longAscii = mensajeAscii.length();
                ratioAscii.setText("100%");
                ratioAritmetica.setText(Float.toString(((float) mensajeAritmetica.length() / (float) longAscii) * 100) + "%");
                ;
            }
        }
    }
}
