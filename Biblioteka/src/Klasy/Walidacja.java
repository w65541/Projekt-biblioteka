package Klasy;

public interface Walidacja {
    default boolean walidacjaSql(String string){
        if(string.contains("czytelnik") || string.contains(";")) throw new RuntimeException("Podejrzany tekst");
        return true;
    }

    default int czyDodatnia(int x){
        if(x>0) return x;
        throw new RuntimeException("Id musi być dodatnie");
    }
}
