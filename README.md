[Berechtigungen auf Android-Geräten](https://developer.android.com/guide/topics/permissions/overview?hl=de)

***Workflow zur Verwendung von Berechtigungen***

Wenn Ihre Anwendung Funktionen bietet, die möglicherweise Zugriff auf eingeschränkte Daten oder eingeschränkte Aktionen erfordern, prüfen Sie, ob Sie die Informationen abrufen oder die Aktionen ausführen können, ohne Berechtigungen erklären zu müssen. Sie können viele Anwendungsfälle in Ihrer App ausführen, z. B. Fotos aufnehmen, die Medienwiedergabe pausieren und relevante Werbung einblenden, ohne Berechtigungen erklären zu müssen.

Wenn Sie entscheiden, dass Ihre App auf eingeschränkte Daten zugreifen oder eingeschränkte Aktionen ausführen muss, um einen Anwendungsfall zu erfüllen, erklären Sie die entsprechenden Berechtigungen. Einige Berechtigungen, auch Berechtigungen zur Installationszeit genannt, werden bei der Installation der Anwendung automatisch gewährt. Bei anderen Berechtigungen, die als Laufzeitberechtigungen bezeichnet werden, muss Ihre Anwendung noch einen Schritt weiter gehen und die Berechtigung zur Laufzeit anfordern.

Abbildung 1 veranschaulicht den Workflow für die Verwendung von App-Berechtigungen:

![Screenshot 2024-09-05 at 15 31 11](https://github.com/user-attachments/assets/1fa774cd-404b-4ac5-804a-b6937e40e757)

[Apps mit Standorterkennung erstellen](https://developer.android.com/develop/sensors-and-location/location?hl=de)

Eine der besonderen Funktionen mobiler Apps ist die Standorterkennung. Mobile Nutzer nehmen ihre Geräte überallhin mit. Durch das Hinzufügen der Standorterkennung zu Ihrer App wird den Nutzern ein stärker kontextbezogenes Erlebnis geboten. Die in den Google Play-Diensten verfügbaren Standort-APIs erleichtern das Hinzufügen der Standorterkennung zu Ihrer App durch automatisches Standort-Tracking, Erkennung von der falschen Straßenseite, Geofencing und Aktivitätserkennung.


![Screenshot 2024-09-05 at 15 40 03](https://github.com/user-attachments/assets/4480ce84-1717-4fce-b592-129d73ca305e)

[Berechtigung zur Standortermittlung anfordern](https://developer.android.com/develop/sensors-and-location/location/permissions?hl=de)

Aus Datenschutzgründen müssen Apps, die Standortdienste verwenden, den Standort anfordern Berechtigungen.

Beachten Sie beim Anfordern von Berechtigungen zur Standortermittlung dieselben Best Practices wie Sie für jede andere Laufzeitberechtigung. Ein wichtiger Unterschied bei den Berechtigungen zur Standortermittlung ist, dass das mehrere Berechtigungen in Bezug auf den Standort hat. Welche Berechtigungen Sie und wie Sie sie anfordern, hängen von den Standortanforderungen für Ihre Anwendungsfall der App.

Auf dieser Seite werden die verschiedenen Standortanforderungen beschrieben und um jeweils die Berechtigung zur Standortermittlung anzufordern.
