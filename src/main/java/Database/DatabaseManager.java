package Database;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    static final String DB_URL = "jdbc:mysql://devel1:3306/Carlos";

    public static List<String> getNewAuftrage(LocalDate date) {
        List<String> auftraege = new ArrayList<>();
        ArrayList<Integer> produktNr = new ArrayList<>();
        ArrayList<Integer> auftragsNr = new ArrayList<>();
        ArrayList<Integer> anzahl = new ArrayList<>();

        try {
            Connection conn = DriverManager.getConnection(DB_URL, "root", "the27");
            Statement stmt = conn.createStatement();
            String query = String.format("Select id, produkt_id, produkt_anzahl from Auftrag where status = \"Offen\" and eingang >= '%s'", date.toString());

            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                produktNr.add(rs.getInt("produkt_id"));
                auftragsNr.add(rs.getInt("id"));
                anzahl.add(rs.getInt("produkt_anzahl"));
            }
            rs.close();

            for (int i = 0; i < produktNr.size(); i++) {
                ResultSet produktName = stmt.executeQuery("Select name from Produkt where id =" + produktNr.get(i));
                while (produktName.next()) {
                    auftraege.add(String.format("%d;%s;%d", auftragsNr.get(i), produktName.getString("name"), anzahl.get(i)));
                }
                produktName.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return auftraege;
    }

    public static void main(String[] args) {
        List<String> newAuftrage = getNewAuftrage(LocalDate.of(2022, 5, 4));
        for (String s : newAuftrage) {
            System.out.println(s);
        }
    }
}


