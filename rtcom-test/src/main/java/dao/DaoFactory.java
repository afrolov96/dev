package dao;


public class DaoFactory {
    private static volatile DaoFactory instance;

    private DaoFactory() {
    }

    public static DaoFactory getInstance() {
        if (instance == null) {
            synchronized (DaoFactory.class) {
                instance = new DaoFactory();
            }
        }
        return instance;
    }

    public AuthDao getAuthDao() {
        return new AuthDao();
    }

    public PersonsDao getPersonsDao() {
        return new PersonsDao();
    }
}
