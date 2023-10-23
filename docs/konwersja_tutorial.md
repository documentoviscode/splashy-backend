# Obsługa plików w Javie

## CSV

odczyt

```java
List<List<String>> data = new ArrayList<>();

try (BufferedReader br = new BufferedReader(new FileReader("data.csv")))
{
    String line;
    while ((line = br.readLine()) != null) {
        String[] values = line.split(COMMA_DELIMITER); // komórki oddzielone przecinkami
        data.add(Arrays.asList(values));
    }
}
```

zapis

```java
BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("data.csv"););

String headers = "FirstName,LastName,Email";
bufferedWriter.write(header);
bufferedWriter.newLine();

String[] data = {"Mikulembe", "Ziemczyk", "documentovisco@pcichowski.com"};
String dataLine = String.join(",", data);
bufferedWriter.write(dataLine);
bufferedWriter.newLine();

bufferedWriter.close();
```

## JSON

dependency

```xml
<dependency>
    <groupId>com.googlecode.json-simple</groupId>
    <artifactId>json-simple</artifactId>
    <version>{version}</version>
</dependency>
```

odczyt

```java
JSONObject json = (JSONObject) readJsonSimpleDemo("data.json");

String name = json.get("name");
String email = json.get("email");

String sub1Date = json.get("subscriptions").get(0).get("date"); // atrybut "date" pierwszego elementu w tablicy "subscriptions"
```

zapis

```java
JSONObject json = new JSONObject();
json.put("name", "Ziemczyk");
json.put("email", "documentovisco@pcichowski.com");

JSONArray subs = new JSONArray();
subs.add("Subscription 1");
subs.add("Subscription 2");

json.put("subscriptions", subs);

subs.get("subscriptions").get(0).put("date", "31-12-2023");
subs.get("subscriptions").get(1).put("date", "31-12-2024");

Files.write("data.json", json.toJSONString().getBytes());
```

## PDF

dependency

```xml
<dependency>
    <groupId>com.itextpdf</groupId>
    <artifactId>itextpdf</artifactId>
    <version>5.5.13.3</version>
</dependency>
```

stworzenie pliku

```java
Document document = new Document();
PdfWriter.getInstance(document, new FileOutputStream("document.pdf"));

// ...

document.close();
```

wstawianie tekstu

```java
Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
Chunk chunk = new Chunk("Title", font);

document.add(chunk);
```

wstawianie obrazka

```java
Path path = Paths.get(ClassLoader.getSystemResource("logo.png").toURI());
Image img = Image.getInstance(path.toAbsolutePath().toString());

document.add(img);
```

wstawianie tabeli

```java
PdfPTable table = new PdfPTable(3); // liczba komórek we wierszu

table.addCell("row 1, col 1");
table.addCell("row 1, col 2");
table.addCell("row 1, col 3");

// ...
```

## XML, DOCX

informacje o potencjalnych formatach [XML](https://www.baeldung.com/java-xml) 
i [DOCX](https://www.baeldung.com/docx4j)