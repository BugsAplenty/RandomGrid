import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.paint.Paint;
import java.net.URL;
import java.util.*;

public class RandomGridController implements Initializable {

    @FXML
    public Button randomizeButton;
    @FXML
    private Canvas canvas;

    private static final int SIZE_CELL = 10;
    private int numCellsHeight;
    private int numCellsWidth;
    private int numCellsBlack;

    @FXML
    void randomizeButtonPushed(ActionEvent event) {
        if (event.getSource() == randomizeButton) {
            cleanGrid();
            drawGrid();
            fillRandom();
        }
    }

    public void drawGrid() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        for (int i = 0; i < numCellsHeight; i++) {
            for(int j = 0; j < numCellsWidth; j++) {
                gc.strokeRect(
                        SIZE_CELL * i,
                        SIZE_CELL * j,
                        SIZE_CELL,
                        SIZE_CELL
                );
                gc.setStroke(Paint.valueOf("BLACK"));
                gc.setLineWidth(1);
            }
        }
    }

    public void fillRandom() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        int[][] idxBlack = generateRandomCoords();
        for (int[] coord: idxBlack) {
            gc.fillRect(
                    SIZE_CELL * coord[0],
                    SIZE_CELL * coord[1],
                    SIZE_CELL,
                    SIZE_CELL
            );
            gc.setFill(Paint.valueOf("RED"));
        }
    }

    private int[][] generateRandomCoords() {
        int[][] coordsRandom = new int[numCellsBlack][2];
        int i = 0;
        while (i < numCellsBlack) {
            int idxRow = new Random().nextInt(numCellsHeight);
            int idxCol = new Random().nextInt(numCellsWidth);
            int[] coordRandom = {idxRow, idxCol};
            if (!Arrays.asList(coordsRandom).contains(coordRandom)) {
                coordsRandom[i][0] = idxRow;
                coordsRandom[i][1] = idxCol;
                i++;
            }
        }
        return coordsRandom;
    }

    public void cleanGrid() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        numCellsWidth = (int) canvas.getHeight()/SIZE_CELL;
        numCellsHeight = (int) canvas.getWidth()/SIZE_CELL;
        numCellsBlack = (numCellsHeight * numCellsWidth) / 10;
        drawGrid();
    }
}
