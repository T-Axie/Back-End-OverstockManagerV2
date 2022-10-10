package digitalcity.demeyert.overstockmanager.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import digitalcity.demeyert.overstockmanager.model.dto.CardDTO;
import digitalcity.demeyert.overstockmanager.model.entity.State;
import digitalcity.demeyert.overstockmanager.model.entity.Language;
import digitalcity.demeyert.overstockmanager.model.entity.Rarity;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

public class CSVHelper {
    public static String TYPE = "text/csv";
    static String[] HEADERs = { "cardmarketId", "name", "gameSet", "language", "count", "foil", "signed", "playset", "rarity", "state", "comment" };

    public static boolean hasCSVFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }

        return true;
    }

    public static List<CardDTO> csvToCards(InputStream is) {
        CSVFormat format = CSVFormat.Builder.create()
                .setDelimiter(';')
                .setHeader(HEADERs)
                .setSkipHeaderRecord(true)
                .build();

        try (
                BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                CSVParser csvParser = new CSVParser(fileReader, format)
        ) {
            List<CardDTO> tutorials = new ArrayList<>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                CardDTO tutorial = new CardDTO(
                        Integer.parseInt(csvRecord.get("cardmarketId")),
                        csvRecord.get("name"),
                        csvRecord.get("gameSet"),
                        Language.valueOf(csvRecord.get("language").toUpperCase()),
                        Integer.parseInt(csvRecord.get("count")),
                        Boolean.parseBoolean(csvRecord.get("foil")),
                        Boolean.parseBoolean(csvRecord.get("signed")),
                        Boolean.parseBoolean(csvRecord.get("playset")),
                        Rarity.valueOf(csvRecord.get("rarity").toUpperCase()),
                        State.valueOf(csvRecord.get("state").toUpperCase()),
                        csvRecord.get("comment")
                );

                tutorials.add(tutorial);
            }
            return tutorials;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

}