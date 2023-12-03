import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class BoutonAleaVideo {
    private JPanel rootPanel;
    private JButton vidéoButton;

    ArrayList<File> fichiers = new ArrayList();

    private void listFichierDansDossier(File dossier) {
        File filesList[] = dossier.listFiles();
        // remplir fichiers et appeler cette fonction récursivement si dossier
        for(File fichier : filesList) {
            if (fichier.isFile()) {
                if (fichier.toString().endsWith(".avi") ||
                        fichier.toString().endsWith(".mp4") ||
                        fichier.toString().endsWith(".ogm") ||
                        fichier.toString().endsWith(".mkv") ||
                        fichier.toString().endsWith(".mpg") ||
                        fichier.toString().endsWith(".VOB") ||
                        fichier.toString().endsWith(".rm") ||
                        fichier.toString().endsWith(".rmvb") ||
                        fichier.toString().endsWith(".divx") ||
                        fichier.toString().endsWith(".MP4") ||
                        fichier.toString().endsWith(".flv") ||
                        fichier.toString().endsWith(".IFO")
                ) {
                    fichiers.add(fichier);
                } else if (
                        !fichier.toString().endsWith(".BUP") &&
                                !fichier.toString().endsWith(".srt") &&
                                !fichier.toString().endsWith(".txt") &&
                                !fichier.toString().endsWith(".smil") &&
                                !fichier.toString().endsWith(".nfo") &&
                                !fichier.toString().endsWith(".jpg") &&
                                !fichier.toString().endsWith(".class") &&
                                !fichier.toString().endsWith(".MF") &&
                                !fichier.toString().endsWith(".doc") &&
                                !fichier.toString().endsWith(".db")
                ) {
                    System.out.println("fichier de type inconnu : "+ fichier.getAbsolutePath());
                }
            } else if (fichier.isDirectory()){
                listFichierDansDossier(fichier);
            }
        }
    }

    public BoutonAleaVideo() {
        vidéoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try
                {
                    //String racine = "I:\\vid\\"; // racine quand en train de bosser, pas pour la version de production/déploiement
                    String racine = "..\\"; // quand en déploiement AleaVideo est placé dans le même dossier que les séries

                    File dossier = new File(racine);
                    fichiers.clear();
                    listFichierDansDossier(dossier);

                    // choisir une des vidéos du dossier
                    int index = (int)(Math.random() * fichiers.size());
                    File fichierChoisi = fichiers.get(index);

                    // remplacer caractères à escaper
                    // lancer vidéo via vlc
                    String cheminFinal = "\"" + fichierChoisi.getAbsoluteFile().toString().replace("\\", "\\\\") + "\"";
                    String commande = "C:\\Program Files\\VideoLAN\\VLC\\vlc.exe "+ cheminFinal;

                    System.out.println("Commande : " + commande);

                    // Running the above command
                    Runtime run  = Runtime.getRuntime();
                    /*Process proc = */run.exec(commande);
                }

                catch (IOException exc)
                {
                    exc.printStackTrace();
                }
            }

            });
        }

    public static void main(String[] args) {
        JFrame frame = new JFrame("BoutonAleaVideo");
        frame.setContentPane(new BoutonAleaVideo().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
