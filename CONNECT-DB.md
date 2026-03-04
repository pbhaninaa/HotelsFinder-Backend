# Connect the database to the backend

## On Railway (deployed backend)

**Do not put your DB password in the code or in the repo.**

1. In Railway, open your **Backend** service (the one from GitHub).
2. Go to **Variables** → **New Variable** → **Add Reference** (or **Reference**).
3. Select your **MySQL** service. Railway will inject:
   - `MYSQLHOST`
   - `MYSQLPORT`
   - `MYSQLUSER` (e.g. root)
   - `MYSQLPASSWORD` (the one you see in MySQL → Credentials)
   - `MYSQLDATABASE`
4. Add one variable yourself:
   - **Name:** `SPRING_PROFILES_ACTIVE`
   - **Value:** `railway`
5. Redeploy the backend. It will connect to MySQL and create/update tables automatically.

Your backend is already configured to use these variables when `spring.profiles.active=railway` (see `application-railway.properties`).

## Locally

Use `application.properties` and your local MySQL (or another profile). Keep credentials only in local overrides or env; never commit real production passwords.
