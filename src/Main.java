import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        Path p = Paths.get("Sales.txt");
        List<List<String>> recordArr = new ArrayList<>();
        HashMap<String, Integer> customers =new HashMap<>();
        HashMap<String, Integer> products = new HashMap<>();
        //StringBuilder sb = new StringBuilder();
        txtToLists(p, recordArr);
        createCustomerMap(recordArr, customers);
        creatProductMap(recordArr, products);
        printResults(customers, products);
    }

    private static void printResults(HashMap<String, Integer> customers, HashMap<String, Integer> products) {
        for(Map.Entry c: customers.entrySet()){
            System.out.println("Customer "+c.getKey()+" total bill balance:\t"+c.getValue());
        }
        System.out.println("-----------------------------------------");
        for(Map.Entry pp: products.entrySet()){
            System.out.println("Product "+pp.getKey()+" total count of purchase:\t"+pp.getValue());
        }
    }

    private static void creatProductMap(List<List<String>> recordArr, Map<String, Integer> products) {
        for (int i = 0; i< recordArr.size(); i++){
            int ItemCount = Integer.parseInt(recordArr.get(i).get(6));
            String TempProduct = recordArr.get(i).get(3);
            if(!products.containsKey(TempProduct)){
                products.put(TempProduct,ItemCount);
            }else{
                ItemCount += products.get(TempProduct);
                products.put(TempProduct,ItemCount);
            }
        }
    }

    private static void createCustomerMap(List<List<String>> recordArr, Map<String, Integer> customers) {
        for (int i = 0; i< recordArr.size(); i++){
            int sum_c = Integer.parseInt(recordArr.get(i).get(5))* Integer.parseInt(recordArr.get(i).get(6));
            String TempCustomer = recordArr.get(i).get(0);
            if(!customers.containsKey(TempCustomer)){
                customers.put(TempCustomer,sum_c);
            }else{
                sum_c += customers.get(TempCustomer);
                customers.put(TempCustomer,sum_c);
            }
        }
    }

    private static void txtToLists(Path p, List<List<String>> recordArr) {
        try(Stream<String> stream = Files.lines(p)){
            stream.forEach(e->{
                List<String> temp = new ArrayList<>();
                StringTokenizer st1 = new StringTokenizer(e, " .'\"-,:;()[]{}`/*+");
                while (st1.hasMoreTokens()) {
                    String token = st1.nextToken();
                    temp.add(token);
                }
                recordArr.add(temp);
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}