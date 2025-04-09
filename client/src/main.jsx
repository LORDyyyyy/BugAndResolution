import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import { Provider } from "@/components/ui/provider";
import "./index.css";
import App from "./App.jsx";

createRoot(document.getElementById("root")).render(
  <Provider>
    <StrictMode>
      <App />
    </StrictMode>
  </Provider>
);
