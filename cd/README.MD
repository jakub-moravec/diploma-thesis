# Příručka k prototypové implementaci

Tento dokument popisuje práci s vytvořenou prototypovou implementací navržené architektury. Konkrétně je popsáno:

1. Prostředí pro sestavení a běh aplikace
2. Kroky nutné pro sestavení aplikace
3. Spuštění aplikace a základní průchod funkcionalitou aplikace

## Prostředí
Součástí CD je spustitelný *JAR* soubor obsahující všechny závislosti, dostačující prostředí pro spuštění prototypové implementace tedy zahrnuje:

- **Operační systém :** Aplikace byla vyvýjena a testována na operačním systému *Windows 10* a ten je tak doporučeným operačním systémem pro běh aplikace. Aplikace by ale měla být spustitelná i na operačních systémech na bázi *Linuxu*.

- **Java :** Pro běh aplikace je nutné mít nainstalovanou *Javu JRE / JDK* ve verzi 7 nebo 8.  

- **Maven :** V případě, že si chce uživatel aplikaci sám sestavit, potřebuje nástroj *Maven* ve verzi *3.5.2*.

## Sestavení aplikace
Přestože je součástí CD spustitelný *JAR* soubor, uživatel si může aplikaci sestavit sám. Sestavení je provedeno pomocí aplikace *Maven*.

Většina používaných knihoven je součástí veřejného *Maven Repository* (nejdůležitějšími jsou knihovny projektu *Spring* a *jUnit*), jsou použity ale také privátní knihovny aplikace *Manta Flow*. Ty jsou na CD ve složce *knihovny* a před sestavením je musí uživatel přemístit do svého lokálního *Maven Repository*. Jedná se o následující knihovny:

- *manta-connector-common-csv* (knihovna *Manta Flow* pro práci s *CSV* soubory)
- *manta-platform-cache* (knihovna *Manta Flow* implementující *cache*)
- *manta-platform-web-core* (knihovna *Manta Flow* obsahující utility pro webové aplikace)
- *manta-dataflow-repository-utils* (knihovna *Manta Flow* obsahující utility pro práci s metadatovým uložištěm)

Seznam všech používaných knihoven a jejich verzí je dohledatelný v *POM* souborech jednotlivých modulů prototypové implementace.

Aplikaci lze sestavit příkazem `mvn clean install` v hlavním adresáři souborové struktury zdrojových kódů aplikace (*%PROJECT_ROOT%*).

Cesta výsledného *JAR* souboru po sestavení je `%PROJECT_ROOT%\manta-dataflow-repository-logic-test\target\manta-dataflow-repository-logic-test-1.0-SNAPSHOT-jar-with-dependencies.jar`.


## Spuštění aplikace

Prototypová implementace navržené architektury slouží především k tomu, aby byl ověřen návrh dílčí architektury komponent aplikace *Manta Flow* vytvořený v diplomové práci. Spouštění prototypové implementace samostatně by tak postrádalo smysl. Pro účely diplomové práce byl ale vytvořen jeden případ užití pro samostatné spouštění prototypové implementace. (Ostatní funkcionalita prototypu je testována pomocí *jUnit* testů.) Ten se skládá z následujících kroků:

1. **Spuštění aplikace :** Protože spustitelný soubor přiložený na CD je tzv. *fat jar*, aplikaci lze spustit jednoduše příkazem `java -jar prototyp.jar` .

2. **Aktualizace metadatového uložiště :** V druhém kroku je uživatel vyzván, aby zadal úplnou cestu k souboru, na základě kterého bude provedena aktualizace metadatového uložiště. Součástí CD jsou vzorové vstupní soubory vhodné pro tento krok (složka *vzorove_vstupy*). Zadaná cesta může vypadat například následovně: `c:\..\input_1.csv` . Struktura vstupního souboru je popsána níže.

3. **Spuštění algoritmu pro dohledání datových toků:** Posledním krokem je zadání uzlu, ze kterého má být provedena analýza datových toků. Ten je potřeba zadat *kvalifikovaným jménem* uzlu. Struktura tohoto jména je `${zdroj}.${databáze}.${tabulka}.${sloupec}`. Příklad existujícího uzlu pro vstupní soubor `input_1.csv` je `resName.db.table1.t1c1`. Po zadání počátečního uzlu je spuštěn algoritmus pro dohledání datových toků. Pokud uzel v metadatovém uložišti neexistuje, je uživatel vyzván k opětovnému zadání kvalifikovaného názvu počátečního uzlu. Pokud algoritmus skončí úspěšně, uživateli jsou zobrazeny názvy uzlů, do kterých vedou přímé datové toky z počátečního uzlu.  

### Struktura vstupního souboru

Vstupní soubor musí být ve formátu *CSV*. Každý řádek souboru reprezentuje jednu entitu, přičemž každá entita musí obsahovat specifické parametry. Níže je seznam entit s příslušnými parametry. Pokud se jedná o konstanty, je ve složených závorkách výčet možností (pro účel prototypu zjednodušený).  

- *layer*: id, název, typ {*PHYSICIAL*}
- *resource*: id, název, typ, popis, id vrstvy
- *node*: id, id rodiče, název, typ {*Database, Table, Column*}, id zdroje
- *node_attribute*: id uzlu, název, hodnota
- *edge*: id hrany, id výchozího uzlu, id koncového uzlu, typ hrany {*DIRECT*}, id zdroje

Entity odpovídají datovému modelu popsanému v diplomové práci a musí být ve vstupním souboru seřazeny stejně, jako jsou v tomto seznamu.   
