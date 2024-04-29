import java.io.*;
import java.util.*;

public class OrderBook {
  private Map<Integer, Integer> bids; // Книга замовлень для продажу
  private Map<Integer, Integer> asks; // Книга замовлень для покупки

  public OrderBook() {
    bids = new TreeMap<>(Collections.reverseOrder()); // Використовуємо TreeMap для bids для автоматичного сортування
    asks = new TreeMap<>();
  }

  public void processInput(String inputFileName, String outputFileName) {
    try (BufferedReader br = new BufferedReader(new FileReader(inputFileName));
         FileWriter fw = new FileWriter(outputFileName)) {
      String line;
      while ((line = br.readLine()) != null) {
        String[] parts = line.split(",");
        String command = parts[0];
        if (command.equals("u")) {
          int price = Integer.parseInt(parts[1]);
          int size = Integer.parseInt(parts[2]);
          String type = parts[3];
          updateOrderBook(price, size, type);
        } else if (command.equals("q")) {
          String query = parts[1];
          if (query.equals("best_bid")) {
            fw.write(getBestBid() + "\n");
          } else if (query.equals("best_ask")) {
            fw.write(getBestAsk() + "\n");
          } else if (query.equals("size")) {
            int price = Integer.parseInt(parts[2]);
            fw.write(getSizeAtPrice(price) + "\n");
          }
        } else if (command.equals("o")) {
          String action = parts[1];
          int size = Integer.parseInt(parts[2]);
          if (action.equals("buy")) {
            executeMarketOrderBuy(size);
          } else if (action.equals("sell")) {
            executeMarketOrderSell(size);
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void updateOrderBook(int price, int size, String type) {
    if (type.equals("bid")) {
      bids.put(price, size);
    } else if (type.equals("ask")) {
      asks.put(price, size);
    }
  }

  private String getBestBid() {
    if (!bids.isEmpty()) {
      int bestPrice = bids.keySet().iterator().next();
      int bestSize = bids.get(bestPrice);
      return bestPrice + "," + bestSize;
    } else {
      return "NA";
    }
  }

  private String getBestAsk() {
    if (!asks.isEmpty()) {
      int bestPrice = asks.keySet().iterator().next();
      int bestSize = asks.get(bestPrice);
      return bestPrice + "," + bestSize;
    } else {
      return "NA";
    }
  }

  private int getSizeAtPrice(int price) {
    if (asks.containsKey(price)) {
      return asks.get(price);
    } else if (bids.containsKey(price)) {
      return bids.get(price);
    } else {
      return 0;
    }
  }

  private void executeMarketOrderBuy(int size) {
    for (Map.Entry<Integer, Integer> entry : asks.entrySet()) {
      int price = entry.getKey();
      int availableSize = entry.getValue();
      if (size >= availableSize) {
        size -= availableSize;
        asks.remove(price);
      } else {
        asks.put(price, availableSize - size);
        break;
      }
    }
  }

  private void executeMarketOrderSell(int size) {
    for (Map.Entry<Integer, Integer> entry : bids.entrySet()) {
      int price = entry.getKey();
      int availableSize = entry.getValue();
      if (size >= availableSize) {
        size -= availableSize;
        bids.remove(price);
      } else {
        bids.put(price, availableSize - size);
        break;
      }
    }
  }

  public static void main(String[] args) {
    OrderBook orderBook = new OrderBook();
    orderBook.processInput("input.txt", "output.txt");
  }
}
