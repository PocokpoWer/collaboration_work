public class User {
    private int id;
    private String name;
    private String city;
    private int age;
    private ShoppingCart shoppingCart;
    private MonetaryAmount balance;

    public class Adrress {
        public enum Country {
            HUNGARY, UK, GERMANY
        }

        private Country country;
        private String city;
    }

    public class ShoppingCart {
    }

    public class MonetaryAmount {
    }
}
