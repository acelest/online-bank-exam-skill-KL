"use client";

import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { useToast } from "@/components/ui/use-toast";
import { api } from "@/lib/api";
import { useStore } from "@/lib/store";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { useState } from "react";

export default function LoginPage() {
  const router = useRouter();
  const { toast } = useToast();
  const { updateUser } = useStore();
  const [isLoading, setIsLoading] = useState(false);

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setIsLoading(true);

    const formData = new FormData(e.currentTarget);
    const username = formData.get("username") as string;
    const password = formData.get("password") as string;

    try {
      console.log("Tentative de connexion avec username:", username);
      const response = await api.auth.login({ username, password });
      console.log("Réponse de login complète:", response);
      console.log("Token reçu:", response.token);
      console.log("Données utilisateur reçues:", response.user);

      // Stockage du token
      localStorage.setItem("token", response.token);
      console.log(
        "Token stocké dans localStorage:",
        localStorage.getItem("token")
      );

      // Stockage des informations utilisateur
      localStorage.setItem("user", JSON.stringify(response.user));
      console.log(
        "User stocké dans localStorage:",
        localStorage.getItem("user")
      );

      // Affichage des balances pour vérification
      console.log("Compte principal:", response.user.primaryAccount);
      console.log("Compte épargne:", response.user.savingsAccount);

      // Mise à jour du store avec les données utilisateur
      updateUser(response.user);

      // Affichage d'un toast de succès
      toast({
        title: "Connexion réussie",
        description: `Bienvenue, ${response.user.firstName} ${response.user.lastName}!`,
      });

      // Redirection vers le tableau de bord
      console.log("Redirection vers le dashboard");
      router.push("/dashboard");
    } catch (error) {
      console.error("Erreur de connexion complète:", error);
      toast({
        title: "Erreur de connexion",
        description: "Nom d'utilisateur ou mot de passe incorrect",
        variant: "destructive",
      });
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-background">
      <div className="w-full max-w-md space-y-8 p-8">
        <div className="text-center">
          <h1 className="text-2xl font-bold">Connexion</h1>
          <p className="text-muted-foreground mt-2">
            Connectez-vous à votre compte bancaire
          </p>
        </div>

        <form onSubmit={handleSubmit} className="space-y-6">
          <div className="space-y-2">
            <Label htmlFor="username">Nom dutilisateur</Label>
            <Input
              id="username"
              name="username"
              type="text"
              required
              disabled={isLoading}
            />
          </div>

          <div className="space-y-2">
            <Label htmlFor="password">Mot de passe</Label>
            <Input
              id="password"
              name="password"
              type="password"
              required
              disabled={isLoading}
            />
          </div>

          <Button type="submit" className="w-full" disabled={isLoading}>
            {isLoading ? (
              <div className="animate-spin h-5 w-5 border-2 border-current border-t-transparent rounded-full" />
            ) : (
              "Se connecter"
            )}
          </Button>
        </form>

        <p className="text-center text-sm text-muted-foreground">
          Pas encore de compte ?{" "}
          <Link
            href="/register"
            className="text-primary hover:text-primary/90 font-medium"
          >
            Créer un compte
          </Link>
        </p>
      </div>
    </div>
  );
}
