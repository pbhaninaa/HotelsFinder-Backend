# Deploy to Railway (Backend + Database)

## 1. Create project and add MySQL

1. Go to [railway.app](https://railway.app) → **New Project**.
2. **Deploy from GitHub repo** → select **pbhaninaa/HotelsFinder-Backend**.
3. Click **+ New** → **Database** → **Add MySQL**. Wait for it to provision.

## 2. Connect backend to MySQL

1. Open the **MySQL** service → **Variables** or **Connect**. Note:
   - **Host**, **Port** (usually 3306), **Database** (e.g. `railway`), **User**, **Password**.
2. Build the JDBC URL:  
   `jdbc:mysql://HOST:PORT/railway?useSSL=true&allowPublicKeyRetrieval=true`
3. Open your **Backend** service (the one from GitHub). Go to **Variables** and add:

| Variable | Value |
|----------|--------|
| `SPRING_PROFILES_ACTIVE` | `railway` |
| `SPRING_DATASOURCE_URL` | `jdbc:mysql://HOST:PORT/railway?useSSL=true&allowPublicKeyRetrieval=true` |
| `SPRING_DATASOURCE_USERNAME` | MySQL user |
| `SPRING_DATASOURCE_PASSWORD` | MySQL password |

4. **Settings** → **Root Directory**: leave **empty** (repo root is the backend).

## 3. Generate public URL

1. Backend service → **Settings** → **Networking** → **Generate Domain**.
2. Copy the URL (e.g. `https://hotelsfinder-backend.up.railway.app`). Use this as **API_URL** in Vercel for the frontend.

## 4. CORS (after frontend is deployed)

1. In the Backend service **Variables**, add:
   - `APP_CORS_ALLOWED_ORIGINS` = your Vercel app URL (e.g. `https://hotelsfinder-frontend.vercel.app`).
2. Redeploy the backend so the change applies.
