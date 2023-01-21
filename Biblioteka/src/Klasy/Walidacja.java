package Klasy;

public interface Walidacja {
    default boolean walidacjaSql(String string) throws RuntimeException{
        if(string.contains("czytelnik") || string.contains(";")) throw new RuntimeException("Podejrzany tekst");
        return true;
    }

    default int czyDodatnia(int x) throws RuntimeException{
        if(x>0) return x;
        throw new RuntimeException("Id musi być dodatnie");
    }

    default String walidacjaTel(String x) throws RuntimeException{
        x=x.replaceAll("[^0-9-]", "");
        if(x.length()==9 || x.isBlank()) return x;
        throw new RuntimeException("Niepoprawny numer telefonu");
    }

    default String walidacjaEmail(String x) throws RuntimeException{
        if(x.contains("@") || x.isBlank()) return x;
        throw new RuntimeException("Niepoprawny e-mail");
    }
}
