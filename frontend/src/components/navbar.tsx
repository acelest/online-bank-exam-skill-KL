import { Button } from "@/components/ui/button";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import { useStore } from "@/lib/store";
import { cn } from "@/lib/utils";
import Link from "next/link";
import { usePathname, useRouter } from "next/navigation";
import { FiUser } from "react-icons/fi";

export function Navbar() {
  const router = useRouter();
  const pathname = usePathname();
  const { user, logout } = useStore();

  const handleLogout = () => {
    logout();
    router.push("/login");
  };

  return (
    <nav className="border-b bg-background">
      <div className="container flex h-16 items-center px-4">
        <Link href="/dashboard" className="font-bold text-xl mr-6">
          Online Banking
        </Link>

        <div className="flex items-center space-x-4 flex-1">
          <DropdownMenu>
            <DropdownMenuTrigger asChild>
              <Button
                variant="ghost"
                className={cn(
                  pathname?.startsWith("/dashboard/primary-account") ||
                    pathname?.startsWith("/dashboard/savings-account")
                    ? "bg-accent"
                    : ""
                )}
              >
                Comptes
              </Button>
            </DropdownMenuTrigger>
            <DropdownMenuContent align="start">
              <DropdownMenuItem
                onClick={() => router.push("/dashboard/primary-account")}
              >
                Compte Principal
              </DropdownMenuItem>
              <DropdownMenuItem
                onClick={() => router.push("/dashboard/savings-account")}
              >
                Compte Épargne
              </DropdownMenuItem>
            </DropdownMenuContent>
          </DropdownMenu>

          <DropdownMenu>
            <DropdownMenuTrigger asChild>
              <Button
                variant="ghost"
                className={cn(
                  pathname?.startsWith("/dashboard/deposit") ||
                    pathname?.startsWith("/dashboard/withdraw") ||
                    pathname?.startsWith("/dashboard/between-accounts")
                    ? "bg-accent"
                    : ""
                )}
              >
                Opérations
              </Button>
            </DropdownMenuTrigger>
            <DropdownMenuContent align="start">
              <DropdownMenuItem
                onClick={() => router.push("/dashboard/deposit")}
              >
                Dépôt
              </DropdownMenuItem>
              <DropdownMenuItem
                onClick={() => router.push("/dashboard/withdraw")}
              >
                Retrait
              </DropdownMenuItem>
              <DropdownMenuItem
                onClick={() => router.push("/dashboard/between-accounts")}
              >
                Virement
              </DropdownMenuItem>
            </DropdownMenuContent>
          </DropdownMenu>

          <Button
            variant="ghost"
            className={cn(
              pathname === "/dashboard/appointment" ? "bg-accent" : ""
            )}
            onClick={() => router.push("/dashboard/appointment")}
          >
            Rendez-vous
          </Button>

          <Link
            href="/dashboard/debug"
            className={cn(
              "relative flex items-center space-x-2 px-4 py-2 text-sm font-medium text-muted-foreground",
              pathname === "/dashboard/debug" && "text-primary font-semibold"
            )}
          >
            Débogage
          </Link>
        </div>

        <div className="flex items-center space-x-4">
          {user && (
            <DropdownMenu>
              <DropdownMenuTrigger asChild>
                <Button variant="ghost" className="flex items-center gap-2">
                  <FiUser className="h-4 w-4" />
                  <span>
                    {user.firstName} {user.lastName}
                  </span>
                </Button>
              </DropdownMenuTrigger>
              <DropdownMenuContent align="end" className="w-56">
                <div className="px-2 py-1.5">
                  <p className="text-sm font-medium">{user.username}</p>
                  <p className="text-xs text-muted-foreground">{user.email}</p>
                </div>
                <DropdownMenuSeparator />
                <DropdownMenuItem
                  onClick={() => router.push("/dashboard/profile")}
                >
                  Mon Profil
                </DropdownMenuItem>
                <DropdownMenuItem
                  onClick={() => router.push("/dashboard/primary-account")}
                >
                  Compte Principal:{" "}
                  {user.primaryAccount?.accountBalance.toFixed(2)}€
                </DropdownMenuItem>
                <DropdownMenuItem
                  onClick={() => router.push("/dashboard/savings-account")}
                >
                  Compte Épargne:{" "}
                  {user.savingsAccount?.accountBalance.toFixed(2)}€
                </DropdownMenuItem>
                <DropdownMenuSeparator />
                <DropdownMenuItem
                  onClick={handleLogout}
                  className="text-red-500"
                >
                  Déconnexion
                </DropdownMenuItem>
              </DropdownMenuContent>
            </DropdownMenu>
          )}
          {!user && (
            <Button variant="outline" onClick={() => router.push("/login")}>
              Se connecter
            </Button>
          )}
        </div>
      </div>
    </nav>
  );
}
