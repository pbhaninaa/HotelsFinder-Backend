# Deploy to Railway (Backend + Database)

## 1. Create project and add MySQL

1. Go to [railway.app](https://railway.app) → **New Project**.
2. **Deploy from GitHub repo** → select **pbhaninaa/HotelsFinder-Backend**.
3. Click **+ New** → **Database** → **Add MySQL**. Wait for it to provision.

## 2. Link MySQL to the Backend (required)

The backend must use Railway’s **reference** so it gets the private MySQL host (connection refused usually means this step was skipped).

1. Open your **Backend** service (the one from GitHub).
2. Go to **Variables** → **New Variable** → **Add Reference** (or **Reference**).
3. Select your **MySQL** service. Railway will add references that inject `MYSQLHOST`, `MYSQLPORT`, `MYSQLUSER`, `MYSQLPASSWORD`, `MYSQLDATABASE` into the backend.
4. Add one more variable by hand:
   - **Variable**: `SPRING_PROFILES_ACTIVE`
   - **Value**: `railway`
5. **Settings** → **Root Directory**: leave **empty** (repo root is the backend).

Do **not** set `SPRING_DATASOURCE_URL` manually; the app uses `MYSQLHOST`, `MYSQLPORT`, etc. from the reference.

## 3. Generate public URL

1. Backend service → **Settings** → **Networking** → **Generate Domain**.
2. Copy the URL (e.g. `https://hotelsfinder-backend.up.railway.app`). Use this as **API_URL** in Vercel for the frontend.

## 4. CORS (after frontend is deployed)

1. In the Backend service **Variables**, add:
   - `APP_CORS_ALLOWED_ORIGINS` = your Vercel app URL (e.g. `https://hotelsfinder-frontend.vercel.app`).
2. Redeploy the backend so the change applies.
