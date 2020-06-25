# Der Alzeheimerkrankenkalender (AKK) Alexa Skill

**Was ist der Alzheimerkrankenkalender...** <br>
Eine Kalenderanwendung, basierend auf einer [AWS Lambda](http://aws.amazon.com/lambda) Funktion, die Verwandte und Pflegekräfte von Alzheimerkranken dabei unterstützen soll, Termine für den Erkrankten einzutragen und diesen daran zu erinnern.
Derzeit sind nur Sprachbefehler zur Verwaltung der Termine möglich. Eintragungen von anderen Personen sind nur am gleichen Alexa Gerät möglich.
Für uns als Team ist es wichtig den Alzheimerkranken, solange möglich, nicht die Möglichkeit zu nehmen selbs zu entscheiden. Daher muss der Kranke jeden Termin, den er nicht selbst erstellt hat Annehmen oder Ablehnen, bevor er für ihn eingetragen wird.


**... und für wen ist er gedacht** <br>
Im Moment kann der Skill von Alzheimerkranken in Deutschland verwendet werden, die noch in der Lage sind ihre eigenen Tätigkeiten und Termine zu erfassen und noch in der Lage sind selbstständig nach Terminen zu fragen.
In Zukunft soll der Skill auch von Kranken genutzt werden können, die nicht mehr in der Lage sind ihre eigenen Termine zu erstellen und eine regelmäßige Erinnerung an Termine benötigen.


**Aktuelle Version:** <br>
Versionsnummer: *0.3.0-PreRelease* <br>
Neuerungen in dieser Version: <br>
*  Hinzufügen von Notizen
*  Bugfixes


**Aktuelle Funktionalität:** <br>
*  Hinzufügen von Terminen über Alexa
*  Hinzufügen von Notizen zu einem Termin
*  Bearbeiten von Terminen über Alexa (Datum und Betreff des Eintrags muss genannt werden)
*  Entfernen von Terminen über Alexa (Datum und Betreff des Eintrags muss genannt werden)
*  Ausgabe der Termine des Tages beim Start des Skills
*  Personenspezifische Speicherung in Datenbank, somit auch auf mehreren Geräten nutzbar
*  Anemeldung über Amazon Account


**Geplant für Folgeversionen:** <br>
*  Webinterface zum Hinzufügen und Bearbeiten von Terminen durch Angehörige
*  Benachrichtigungen für aufkommende Termine 
*  Termine kategorisieren
