### Building and running your application

When you're ready, start your application by running:
`docker compose up --build`.

If you want to personalize and read enviroment variables from .env file, use:
`docker compose --env-file <env file directory> up --build`

If you want to run on detached mode add `-d` at the end.

Your application will be available at http://localhost:8080.
