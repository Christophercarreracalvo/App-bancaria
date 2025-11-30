package com.bankapp.app;

import com.bankapp.service.AccountService;
import com.bankapp.model.Account;

import java.util.List;
import java.util.Scanner;

public class ConsoleMenu {
    private static AccountService service;

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            service = new AccountService();
            boolean exit = false;
            while (!exit) {
                System.out.println("--- Banco - Menú ---");
                System.out.println("1) Crear cuenta");
                System.out.println("2) Listar cuentas");
                System.out.println("3) Consultar cuenta por número");
                System.out.println("4) Depositar");
                System.out.println("5) Retirar");
                System.out.println("6) Transferir");
                System.out.println("0) Salir");
                System.out.print("Seleccione una opción: ");
                String opt = sc.nextLine();
                try {
                    switch (opt) {
                        case "1":
                            System.out.print("ID cliente (int): "); int cid = Integer.parseInt(sc.nextLine());
                            System.out.print("Número de cuenta: "); String accNum = sc.nextLine();
                            System.out.print("Saldo inicial (double): "); double bal = Double.parseDouble(sc.nextLine());
                            Account created = service.createAccount(cid, accNum, bal);
                            System.out.println("Cuenta creada: " + created);
                            break;
                        case "2":
                            List<Account> list = service.listAccounts();
                            for (Account a : list) System.out.println(a);
                            break;
                        case "3":
                            System.out.print("Número de cuenta: "); String q = sc.nextLine();
                            Account found = service.findByNumber(q);
                            System.out.println(found == null ? "No encontrada" : found);
                            break;
                        case "4":
                            System.out.print("Número de cuenta: "); String dacc = sc.nextLine();
                            System.out.print("Monto a depositar: "); double dam = Double.parseDouble(sc.nextLine());
                            service.deposit(dacc, dam);
                            System.out.println("Depósito realizado");
                            break;
                        case "5":
                            System.out.print("Número de cuenta: "); String racc = sc.nextLine();
                            System.out.print("Monto a retirar: "); double ram = Double.parseDouble(sc.nextLine());
                            service.withdraw(racc, ram);
                            System.out.println("Retiro realizado");
                            break;
                        case "6":
                            System.out.print("Cuenta origen: "); String from = sc.nextLine();
                            System.out.print("Cuenta destino: "); String to = sc.nextLine();
                            System.out.print("Monto: "); double mam = Double.parseDouble(sc.nextLine());
                            service.transfer(from, to, mam);
                            System.out.println("Transferencia realizada");
                            break;
                        case "0":
                            exit = true; break;
                        default:
                            System.out.println("Opción inválida");
                    }
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
            System.out.println("Saliendo..."); 
        } catch (Exception e) {
            System.err.println("Fallo al iniciar la aplicación: " + e.getMessage());
        }
    }
}