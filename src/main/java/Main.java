import entity.Buy;
import entity.Product;
import entity.Shopper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


import java.util.Scanner;

public class Main {

    private static Session session = null;

    final private static SessionFactory sessionFactory = new Configuration()
            .configure("hibernate.cfg.xml")
            .addAnnotatedClass(Product.class)
            .addAnnotatedClass(Shopper.class)
            .addAnnotatedClass(Buy.class)
            .buildSessionFactory();


    public static void main(String[] args) {

        try {
            /*
            addProductsAndShoppers();
            showProductsByPerson("ivan");
            showProductsByPerson("boris");
            findPersonsByProductTitle("sprite");
            findPersonsByProductTitle("cola");
            removeProduct("cola");
            removeProduct("cola");
            removePerson("boris");
            removePerson("boris");
            showProductsByPerson("ivan");
            addShopperAndCola();
            buy("ivan", "cola");
            showProductsByPerson("ivan");
            */

            while (true) {
                Scanner scanner = new Scanner(System.in);
                String[] query = scanner.nextLine().split(" ");
                if (query.length < 2) {
                    break;
                }
                if (query[0].equals("/showProductsByPerson")) {
                    showProductsByPerson(query[1]);
                } else if (query[0].equals("/findPersonsByProductTitle")) {
                    findPersonsByProductTitle(query[1]);
                } else if (query[0].equals("/removeProduct")) {
                    removeProduct(query[1]);
                } else if (query[0].equals("/removePerson")) {
                    removePerson(query[1]);
                } else if (query.length == 3 && query[0].equals("/buy")) {
                    buy(query[1], query[2]);
                } else break;


            }

        } finally {
            sessionFactory.close();
            session.close();
            System.out.println("session close");
        }

    }

    static void addShopperAndCola() {
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Product cola = new Product("cola", 11.05);
        Product sprite = session.get(Product.class, 6L);
        System.out.println();
        Shopper boris = new Shopper("boris");
        session.persist(boris);
        session.persist(cola);
        session.persist(new Buy(cola, boris));
        session.persist(new Buy(sprite, boris));
        session.getTransaction().commit();

    }

    static void addProductsAndShoppers() {
        Product cola = new Product("cola", 100.05);
        Product sprite = new Product("sprite", 75.05);
        Product orange = new Product("orange", 50.05);
        Shopper ivan = new Shopper("ivan");
        Shopper boris = new Shopper("boris");
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.persist(cola);
        session.persist(sprite);
        session.persist(orange);
        session.persist(ivan);
        session.persist(boris);
        session.persist(new Buy(cola, ivan));
        session.persist(new Buy(sprite, ivan));
        session.persist(new Buy(sprite, boris));
        session.persist(new Buy(orange, boris));
        session.getTransaction().commit();
        sessionFactory.close();
        session.close();

    }


    static void showProductsByPerson(String personName) {
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        System.out.println("******************");
        System.out.println(personName);
        System.out.println("------------------");
        session.createQuery(
                        "from Shopper where name = :query", Shopper.class)
                .setParameter("query", personName)
                .uniqueResult()
                .getBuys()
                .forEach(f -> System.out.println(f.getProductId().toString()));
        System.out.println("******************\n");
        session.getTransaction().commit();
    }

    static void findPersonsByProductTitle(String productTitle) {
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        System.out.println("******************");
        System.out.println(productTitle);
        System.out.println("------------------");
        session.createQuery(
                        "from Product where title = :productTitle", Product.class)
                .setParameter("productTitle", productTitle)
                .getSingleResultOrNull()
                .getBuys().stream()
                .map(boy -> boy.getShopperId())
                .forEach(System.out::println);
        System.out.println("******************\n");
        session.getTransaction().commit();
    }

    static void removePerson(String personName) {
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Shopper shopper = session.createQuery(
                        "from Shopper where name = :query", Shopper.class)
                .setParameter("query", personName).getSingleResultOrNull();
        if (shopper != null) {
            session.remove(shopper);
        } else {
            System.out.println("Shopper not found!!!!!!!!!!!!!!!!!!!!!!!!");
        }
        session.getTransaction().commit();

    }

    static void removeProduct(String productTitle) {
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Product product = session.createQuery(
                        "from Product where title = :query", Product.class)
                .setParameter("query", productTitle).getSingleResultOrNull();
        if (product != null) {
            session.remove(product);
        } else {
            System.out.println("Product not found!!!!!!!!!!!!!!!!!!!!!!!!");
        }
        session.getTransaction().commit();
    }


    static void buy(String personName, String productTitle) {
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Shopper shopper = session.createQuery(
                        "from Shopper where name = :query", Shopper.class)
                .setParameter("query", personName).getSingleResultOrNull();

        Product product = session.createQuery(
                        "from Product where title = :query", Product.class)
                .setParameter("query", productTitle).getSingleResultOrNull();
        if (product != null && shopper != null) {
            session.persist(new Buy(product, shopper));
        }
        session.getTransaction().commit();
    }

}
