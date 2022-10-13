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
    static String[] HEADERs = { "cardmarketId", "name", "gameSet", "language", "quantity", "isFoil", "isSigned", "isPlayset", "rarity", "condition", "comment" };

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
                        Language.fromLabel(csvRecord.get("language")),
                        Integer.parseInt(csvRecord.get("quantity")),
                        Boolean.parseBoolean(csvRecord.get("isFoil")),
                        Boolean.parseBoolean(csvRecord.get("isSigned")),
                        Boolean.parseBoolean(csvRecord.get("isPlayset")),
                        Rarity.valueOf(csvRecord.get("rarity").toUpperCase()),
                        State.valueOf(csvRecord.get("condition").toUpperCase()),
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