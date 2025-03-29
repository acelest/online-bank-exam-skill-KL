import { create } from "zustand";
import type { User } from "./api";
import { api } from "./api";

interface UserState {
  user: User | null;
  isLoading: boolean;
  error: string | null;
  fetchUser: () => Promise<void>;
  setUserFromLocalStorage: () => boolean;
  updateUser: (userData: User) => void;
  logout: () => void;
}

export const useStore = create<UserState>((set) => ({
  user: null,
  isLoading: false,
  error: null,

  fetchUser: async () => {
    try {
      console.log(
        "fetchUser: Début de la récupération des données utilisateur"
      );
      set({ isLoading: true, error: null });

      // D'abord essayer de récupérer depuis le localStorage
      console.log("fetchUser: Vérification du localStorage");
      const storedUser = localStorage.getItem("user");

      if (storedUser) {
        console.log("fetchUser: User trouvé dans localStorage");
        try {
          const user = JSON.parse(storedUser);
          console.log("fetchUser: User parsé depuis localStorage:", user);
          set({ user, isLoading: false });
          console.log(
            "fetchUser: Store mis à jour avec données du localStorage"
          );
          return;
        } catch (e) {
          console.error(
            "fetchUser: Erreur de parsing du user depuis localStorage:",
            e
          );
        }
      } else {
        console.log("fetchUser: Aucun user trouvé dans localStorage");
      }

      // Si pas dans localStorage ou erreur de parsing, appeler l'API
      console.log("fetchUser: Appel de l'API");
      const token = localStorage.getItem("token");
      console.log("fetchUser: Token disponible:", !!token);

      if (!token) {
        throw new Error("Token manquant");
      }

      const user = await api.user.getProfile();
      console.log("fetchUser: Données reçues de l'API:", user);

      // Sauvegarder dans localStorage pour le futur
      localStorage.setItem("user", JSON.stringify(user));

      set({ user, isLoading: false });
      console.log("fetchUser: Store mis à jour avec données de l'API");
    } catch (error) {
      console.error("fetchUser: Erreur lors de la récupération:", error);
      set({
        error: "Erreur lors de la récupération des données utilisateur",
        isLoading: false,
      });
      // Si l'erreur est due à un token invalide, rediriger vers la page de connexion
      if (error instanceof Error && error.message.includes("401")) {
        console.log("fetchUser: Erreur 401 détectée, redirection vers login");
        localStorage.removeItem("token");
        localStorage.removeItem("user");
        window.location.href = "/login";
      }
    }
  },

  setUserFromLocalStorage: () => {
    try {
      console.log(
        "setUserFromLocalStorage: Tentative de récupération depuis localStorage"
      );
      const storedUser = localStorage.getItem("user");

      if (storedUser) {
        console.log("setUserFromLocalStorage: User trouvé dans localStorage");
        const user = JSON.parse(storedUser);
        console.log("setUserFromLocalStorage: User parsé:", user);
        set({ user });
        console.log("setUserFromLocalStorage: Store mis à jour");
        return true;
      } else {
        console.log(
          "setUserFromLocalStorage: Aucun user trouvé dans localStorage"
        );
        return false;
      }
    } catch (error) {
      console.error("setUserFromLocalStorage: Erreur:", error);
      return false;
    }
  },

  updateUser: (userData: User) => {
    set({ user: userData });
    localStorage.setItem("user", JSON.stringify(userData));
    console.log(
      "updateUser: Données utilisateur mises à jour dans le store et localStorage"
    );
  },

  logout: () => {
    localStorage.removeItem("token");
    localStorage.removeItem("user");
    set({ user: null });
    console.log("logout: Déconnexion effectuée");
  },
}));
