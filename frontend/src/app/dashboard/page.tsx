"use client";

import { Icons } from "@/components/icons";
import { Button } from "@/components/ui/button";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
// import { useToast } from "@/components/ui/use-toast";
import { useStore } from "@/lib/store";
import { useRouter } from "next/navigation";
import { useEffect } from "react";

export default function DashboardPage() {
  const router = useRouter();
  // const { toast } = useToast();
  const { user, isLoading, error, fetchUser, setUserFromLocalStorage } =
    useStore();

  useEffect(() => {
    console.log("DashboardPage: Initialisation de la page");

    // D'abord essayer de charger depuis le localStorage
    const userLoaded = setUserFromLocalStorage();
    console.log(
      "DashboardPage: Utilisateur chargé depuis localStorage:",
      userLoaded
    );

    // Peu importe le résultat, on essaie toujours de récupérer les données fraîches
    console.log("DashboardPage: Appel de fetchUser");
    fetchUser().catch((err) => {
      console.error("DashboardPage: Erreur lors de fetchUser:", err);
    });
  }, [fetchUser, setUserFromLocalStorage]);

  useEffect(() => {
    console.log(
      "DashboardPage: Affichage des données utilisateur actuelles dans le store:",
      user
    );
  }, [user]);

  if (isLoading && !user) {
    console.log("DashboardPage: Affichage du spinner de chargement");
    return (
      <div className="flex items-center justify-center min-h-[50vh]">
        <div className="animate-spin h-8 w-8 border-4 border-primary border-t-transparent rounded-full" />
      </div>
    );
  }

  if (error && !user) {
    console.log("DashboardPage: Affichage de l'erreur:", error);
    return (
      <div className="flex flex-col items-center justify-center min-h-[50vh] space-y-4">
        <p className="text-destructive">{error}</p>
        <Button onClick={() => router.push("/login")}>
          Retourner à la connexion
        </Button>
      </div>
    );
  }

  if (!user) {
    console.log("DashboardPage: Redirection vers login car aucun utilisateur");
    router.push("/login");
    return null;
  }

  return (
    <div className="space-y-8">
      <div className="flex items-center justify-between">
        <h1 className="text-3xl font-bold">
          Bienvenue, {user.firstName} {user.lastName}
        </h1>
      </div>

      {/* Information de l'utilisateur */}
      <Card className="p-6">
        <CardHeader className="px-0 pt-0">
          <CardTitle>Informations personnelles</CardTitle>
          <CardDescription>Vos informations de compte</CardDescription>
        </CardHeader>
        <CardContent className="px-0 pt-0">
          <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
            <div>
              <p className="text-sm font-medium text-muted-foreground">
                Nom dutilisateur
              </p>
              <p className="text-base">{user.username}</p>
            </div>
            <div>
              <p className="text-sm font-medium text-muted-foreground">Email</p>
              <p className="text-base">{user.email}</p>
            </div>
            <div>
              <p className="text-sm font-medium text-muted-foreground">
                Téléphone
              </p>
              <p className="text-base">{user.phone}</p>
            </div>
            <div>
              <p className="text-sm font-medium text-muted-foreground">
                ID Utilisateur
              </p>
              <p className="text-base">{user.userId}</p>
            </div>
          </div>
        </CardContent>
      </Card>

      <div className="grid gap-6 md:grid-cols-2">
        {/* Compte Principal */}
        <Card className="p-6">
          <div className="flex items-start justify-between">
            <div>
              <h2 className="text-xl font-semibold mb-2">Compte Principal</h2>
              <p className="text-3xl font-bold">
                {user.primaryAccount
                  ? user.primaryAccount.accountBalance.toLocaleString("fr-FR", {
                      style: "currency",
                      currency: "EUR",
                    })
                  : "N/A"}
              </p>
              <p className="text-sm text-muted-foreground mt-1">
                N°{" "}
                {user.primaryAccount
                  ? user.primaryAccount.accountNumber
                  : "N/A"}
              </p>
              <p className="text-sm text-muted-foreground mt-1">
                ID: {user.primaryAccount ? user.primaryAccount.id : "N/A"}
              </p>
            </div>
            <Button
              variant="ghost"
              size="icon"
              onClick={() => router.push("/dashboard/primary-account")}
            >
              <Icons.arrowRight className="h-5 w-5" />
            </Button>
          </div>
        </Card>

        {/* Compte Épargne */}
        <Card className="p-6">
          <div className="flex items-start justify-between">
            <div>
              <h2 className="text-xl font-semibold mb-2">Compte Épargne</h2>
              <p className="text-3xl font-bold">
                {user.savingsAccount
                  ? user.savingsAccount.accountBalance.toLocaleString("fr-FR", {
                      style: "currency",
                      currency: "EUR",
                    })
                  : "N/A"}
              </p>
              <p className="text-sm text-muted-foreground mt-1">
                N°{" "}
                {user.savingsAccount
                  ? user.savingsAccount.accountNumber
                  : "N/A"}
              </p>
              <p className="text-sm text-muted-foreground mt-1">
                ID: {user.savingsAccount ? user.savingsAccount.id : "N/A"}
              </p>
            </div>
            <Button
              variant="ghost"
              size="icon"
              onClick={() => router.push("/dashboard/savings-account")}
            >
              <Icons.arrowRight className="h-5 w-5" />
            </Button>
          </div>
        </Card>
      </div>

      <div className="grid gap-4 md:grid-cols-3">
        <Button
          className="h-24"
          onClick={() => router.push("/dashboard/deposit")}
        >
          <Icons.wallet className="mr-2 h-5 w-5" />
          Dépôt
        </Button>

        <Button
          className="h-24"
          onClick={() => router.push("/dashboard/withdraw")}
        >
          <Icons.creditCard className="mr-2 h-5 w-5" />
          Retrait
        </Button>

        <Button
          className="h-24"
          onClick={() => router.push("/dashboard/between-accounts")}
        >
          <Icons.arrowLeftRight className="mr-2 h-5 w-5" />
          Virement
        </Button>
      </div>
    </div>
  );
}
