# junker.dev

[![CI Status](https://circleci.com/gh/jakejunk/junker.dev.svg?style=shield)](https://app.circleci.com/pipelines/github/jakejunk/junker.dev)

## Running Locally With HTTPS

1. Ensure that `docker-compose` is installed on your machine.
2. In `deploy/certbot/conf/live/junker.dev/`, generate a self-signed certificate `fullchain.pem` and private key `privkey.pem`:
   1. E.g. using OpenSSL: `openssl req -x509 -nodes -days 3650 -newkey rsa:2048 -keyout privkey.pem -out fullchain.pem -extensions 'v3_req' -subj "/CN=localhost" -addext "subjectAltName=DNS:localhost"`
3. (Optional) Trust the certificate in your development browser.
4. Use the `Local Compose Deployment` run configuration.
