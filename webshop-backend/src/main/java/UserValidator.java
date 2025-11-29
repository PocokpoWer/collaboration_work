public class UserValidator implements Validator<User>{

    //TODO
    // user-id: ellenőrzés: pl, pozitív szám legyen
    // user-name: ellenőrzés pl., nem legyen null
    // user-age: 18+-os és legyen felső határa is
    // user-addres.getCity: ne legyen üres
    // user-addres.getCountry: ne legyen null
    // user-shoppingcart: ellenőrzés: ne legyen null
    // user-balance: pozitív legyen

    //FIXME : null-t is ki kell zárni

    @Override
    public boolean isValid(User user) {
        if (user.getId() <= 0)
            return false;
        if (user.getName().trim().isEmpty())
            return false;
        if (user.getAge() <= 18 || user.getAge() >= 105)
            return false;
        if (user.getBalance().getAmount() < 0)
            return false;
        if (user.getAddress().getCity().trim().isEmpty())
            return false;
        if (user.getShoppingCart() == null) return false;
        return user.getAddress().getCountry() != null;
    }
}