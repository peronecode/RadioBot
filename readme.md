# RadioBot API + Local Player

## Hardware
Simple radio research/project that created a Weather Radio Station.
<br>
Was used one `Baofeng UV-5R` as a receptor and one `Baofeng GT-3TP` combined with `APRS-K1` kit cable as transmitter.
<br>
The vox menu on the transmitter Baofeng was set as 1.

# API
Was used Java as a primary language due the easy ability to handle the creation of the API future deployments.
Combined with Spring Boot, this application simply:
- Consume Accu Weather free API to get the region weather data;
- Replace a text template parameters for a real weather data parameters;
- Consume the Google Cloud TTS (Text To Speech) API sending the text and getting Base64 encoded audio file;
- Decode the response into a MP3 file and play it locally on my OS.

# Endpoints
Import the RadioBot Postman collection file called `API.postman_collection.json` to check the internal and external endpoints.

# API Keys
I did have some `environment variables` for those settings, but you can replace on the application `YAML` file.

# Finals
This project was created just to test if it can be possible to handle weather stations at home using simple technologies and simple radios.
<br>
Even having my Cat3 ham radio license, I cannot simply install this at my balcony and let it play hourly without appropriate equipments and station license.

Luccas Perone
<br>
`CR7BDG`