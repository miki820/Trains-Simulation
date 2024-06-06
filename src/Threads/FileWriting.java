package Threads;

import Train.TrainComposition;
import Train.Wagons.Wagon;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

//Class for file writing to save simulation state every 5 seconds
public class FileWriting extends Thread {

    List<TrainComposition> trainCompositionList;

    public FileWriting(List<TrainComposition> trainCompositionList) {
        this.trainCompositionList = trainCompositionList;
    }

    //Run Method that starts with the thread
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                sortTrainWagonsByWeight(trainCompositionList);
                writeToFile(trainCompositionList);
                Thread.sleep(5000);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //Method that sorts wagons in every train by their weight
    private static void sortTrainWagonsByWeight(List<TrainComposition> trainCompositionList) {
        //Comparator that helps to sort wagons by their weight
        Comparator<Wagon> weightComparator = (o1, o2) -> (int) (o1.getGrossWeight() - o2.getGrossWeight());

        for (TrainComposition trainComposition : trainCompositionList) {
            trainComposition.getWagons().sort(weightComparator);
        }
    }

    //Static Method that writes to file trains composition
    private static void writeToFile(List<TrainComposition> trainCompositionList) throws IOException {
        File file = new File("src/AppState.txt");

        if (!file.exists()) {
            file.createNewFile();
        }

        try (FileOutputStream fos = new FileOutputStream(file)) {
            for (TrainComposition trainComposition : trainCompositionList) {
                fos.write(trainComposition.toString().getBytes());
                fos.write("\n".getBytes());
            }
        }
    }
}



