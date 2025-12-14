package grid;


import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStreamReader;


/**
 * Trieda LevelGrid predstavuje siet stvorcov na ktoru je plocha rozdelena
 */
public class LevelGrid {

    private double x;
    private double y;

    private int xmin;
    private int ymin;
    private int xmax;
    private int ymax;

    private final double tween;

    private int[][] mapa;

    private final int velkostPolicka;

    private int pocetRiadkov;
    private int pocetStlpcov;
    private int sirka;
    private int vyska;


    private BufferedImage tileset;
    private int numTilesAcross;

    private Tile[][] policka;

    private int riadokOffset;
    private int stlpecOffset;

    private final int pocetRiadkovNaVykresl;
    private final int pocetStlpcovNaVykresl;


    /**
     * Konstrutktor nastavi velkost policka podla parametra,
     * nastavi pocet riadkov ktore sa maju vykreslit na urcity pocet
     * podla velkosti okna a velkosti policka
     *
     * @param velkostPolicka velkost policka
     */
    public LevelGrid(int velkostPolicka) {
        this.velkostPolicka = velkostPolicka;
        this.pocetRiadkovNaVykresl = GamePanel.HEIGHT / velkostPolicka + 2;
        this.pocetStlpcovNaVykresl = GamePanel.WIDTH / velkostPolicka + 2;
        this.tween = 1;


    }


    /**
     * @param x poloha X
     */
    public void setX(double x) {
        this.x = x;
    }


    /**
     * @param y poloha X
     */
    public void setY(double y) {
        this.y = y;
    }


    /**
     * Vytvori dvojrozmerne pole policka a do neho vklada
     * Tile ktoreho  parametre su subImage a typ tilu NORMAL alebo BLOCKED
     * cyklus for prebehne numTilesAcross krat a z png sandTiles si vybera subImages
     *
     * @param fileName cesta k obrazku
     */
    public void loadTiles(String fileName) {
        try {
            this.tileset = ImageIO.read(new File(fileName));

            this.numTilesAcross = this.tileset.getWidth() / velkostPolicka;
            this.policka = new Tile[2][numTilesAcross];

            BufferedImage subImage;
            for (int i = 0; i < numTilesAcross; i++) {
                subImage = tileset.getSubimage(i * this.velkostPolicka, 0, this.velkostPolicka, this.velkostPolicka);
                this.policka[0][i] = new Tile(subImage, Tile.NORMAL);
                subImage = tileset.getSubimage(i * this.velkostPolicka, this.velkostPolicka, this.velkostPolicka, this.velkostPolicka);
                this.policka[1][i] = new Tile(subImage, Tile.BLOCKED);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Metoda nacitava hodnoty z level.map po riadkoch a stlpcoch
     * a uklada ich do mapy
     *
     * @param s cesta k suboru.map
     */
    public void loadMap(String s) {
        try {

            InputStream input = new FileInputStream(s);
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(input)
            );

            this.pocetStlpcov = Integer.parseInt(br.readLine());
            this.pocetRiadkov = Integer.parseInt(br.readLine());

            this.mapa = new int[pocetRiadkov][pocetStlpcov];

            this.sirka = this.pocetStlpcov * this.velkostPolicka;
            this.vyska = this.pocetRiadkov * this.velkostPolicka;

            this.xmin = GamePanel.WIDTH - this.sirka;
            this.xmax = 0;
            this.ymin = GamePanel.HEIGHT - this.vyska;
            this.ymax = 0;

            String oddelovac = "\\s+";

            for (int i = 0; i < this.pocetRiadkov; i++) {
                String riadok = br.readLine();
                String[] tokens = riadok.split(oddelovac);
                for (int j = 0; j < this.pocetStlpcov; j++) {
                    this.mapa[i][j] = Integer.parseInt(tokens[j]);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * @return velkost policka
     */
    public int getTileSize() {
        return velkostPolicka;
    }

    /**
     * @return x x suradnica
     */
    public int getX() {
        return (int)x;
    }

    /**
     * @return y y suradnica
     */
    public int getY() {
        return (int)y;
    }

    /**
     * @return sirka sirka mapy
     */
    public int getWidth() {
        return sirka;
    }

    /**
     * @return vyska sirka mapy
     */
    public int getHeight() {
        return vyska;
    }

    /**
     * Vrati 0 alebo 1 podla toho ci sa subImage Tilu nachadzal v hornom alebo spodnok riadku sandTiles.png
     *
     * @param riadok riadok
     * @param stlpec stlpec
     * @return typ tilu
     */
    public int getType(int riadok, int stlpec) {


        if ((riadok >= 0) && (stlpec >= 0)) {
            int cr = mapa[riadok][stlpec];

            int r = cr / numTilesAcross;
            int c = cr % numTilesAcross;

            return this.policka[r][c].getType();
        }

        return 0;


    }


    /**
     * Nastavi poziciu mapy
     *
     * @param x x suradnica
     * @param y y suradnica
     */
    public void setPosition(double x, double y) {
        this.x += (x - this.x) * tween;
        this.y += (y - this.y) * tween;

        this.fixHranice();

        this.stlpecOffset = (int)-this.x / this.velkostPolicka;
        this.riadokOffset = (int)-this.y / this.velkostPolicka;


    }

    /**
     * nastavi x a y suradnice podla max a min suradnic mapy
     * ak je x suradnica mensia ako stanovena minimalna
     * hodnota nastavi x na minimalu atd.
     */
    public void fixHranice() {
        if (this.x < this.xmin) {
            this.x = this.xmin;
        }
        if (this.y < this.ymin) {
            this.y = this.ymin;
        }
        if (this.x > this.xmax) {
            this.x = this.xmax;
        }
        if (this.y > this.ymax) {
            this.y = this.ymax;
        }

    }


    /**
     * Vykresluje na platno subImage z Tilov podla hodnot v mape
     * napr. ak je v subore na urcitom mieste 0 tato hodnota je priradena
     * k grafickemu zobrazeniu teda nulty subimage v sandTiles je transparenty a je
     * v prvom riadku to znamena ze nie je BLOCKED ale je NORMAL a hrac cez neho moze prechadzat
     * naopak ked sa vykresluje policko 21 ktore je z druheho riadku sandTiles je BLOCKED a hrac cez neho
     * nemoze prechadzat
     * <p>
     * Podobny system ako v ZEMEPLOCHE :)
     *
     * @param g tvar
     */
    public void draw(Graphics2D g) {
        for (int i = this.riadokOffset; i < this.riadokOffset + this.pocetRiadkovNaVykresl; i++) {

            if (i >= this.pocetRiadkov) {
                break;
            }

            for (int j = this.stlpecOffset; j < this.stlpecOffset + this.pocetStlpcovNaVykresl; j++) {
                if (j >= this.pocetStlpcov) {
                    break;
                }

                if (this.mapa[i][j] == 0) {
                    continue;
                }

                int rc = this.mapa[i][j];

                int r = rc / this.numTilesAcross;
                int c = rc % this.numTilesAcross;

                g.drawImage(this.policka[r][c].getImage(), (int)x + j * this.velkostPolicka, (int)y + i * velkostPolicka, null);


            }
        }
    }


}
