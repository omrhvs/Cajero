package cajero;

import static cajero.Cajero.lines;
import java.util.Scanner;
import javax.swing.JOptionPane;

class Saldo 
{
    protected double saldo;
    public Saldo(double saldo) 
    {
        this.saldo = saldo;
    }
}

class Consulta extends Saldo 
{
    public Consulta(double saldo)
    {
        super(saldo);
    }
    
    public double consultarSaldo() 
    {
        return this.saldo;
    }
}

class Deposito extends Saldo 
{
    public Deposito(double saldo) 
    {
        super(saldo);
    }
    
    public double realizarDeposito(double monto) 
    {
        this.saldo += monto;
        return this.saldo;
    }
}

class Retiro extends Saldo
{
    public Retiro(double saldo) 
    {
        super(saldo);
    }
    
    public double realizarRetiro(double monto) 
    {
        if (monto > this.saldo) 
        {
            JOptionPane.showInputDialog(null,Cajero.lines + "\nFondos insuficientes. Transaccion cancelada.\n" + Cajero.lines);
            return this.saldo;
        } 
        else
        {
            this.saldo -= monto;
            return this.saldo;
        }
    }
}

public class Cajero
{
    public static final String lines = "==============================";
    
    public static void main(String[] args) 
    {
        Scanner scanner = new Scanner(System.in);
        String nip;
        int intentosActuales = 0;
        
        do
        {
            nip = JOptionPane.showInputDialog(null,
                    lines + 
                    "\nIngrese su NIP (" +
                    intentosActuales + 
                    "/3)\n" +
                    lines
            );
            
            intentosActuales++;
        }
        while(!nip.equals("1234") && intentosActuales != 3);
        
        if(intentosActuales >= 3)
        {
            JOptionPane.showMessageDialog(null, lines + "\nCuenta bloqueada por seguridad.\n" + lines);
            System.exit(0);
        }
        
        double saldoInicial;
        Saldo cuenta;
        
        do
        {
            saldoInicial = Double.parseDouble(JOptionPane.showInputDialog(null,lines + "\nIngresa tu saldo inicial\n" + lines));
            cuenta = new Saldo(saldoInicial);
        }
        while(saldoInicial <= 0);
        
        while (true) 
        {
            int opcion;
            
            opcion = Integer.parseInt(JOptionPane.showInputDialog(null,
                    lines + "\nSeleccione una opción: \n" + lines +
                    "\n1. Consultar saldo" +
                    "\n2. Realizar depósito" +
                    "\n3. Realizar retiro" +
                    "\n4. Salir\n" + lines
            ));
            
            switch (opcion) 
            {
                case 1:
                    Consulta consulta = new Consulta(cuenta.saldo);
                    JOptionPane.showMessageDialog(null,lines + "\nSu saldo actual es: $" + consulta.consultarSaldo() + "\n" + lines);
                    break;
                    
                case 2:
                    double montoDeposito;
                    montoDeposito = Double.parseDouble(JOptionPane.showInputDialog(null,lines + "\nIngrese el monto a depositar\n" + lines));
                    Deposito deposito = new Deposito(cuenta.saldo);
                    cuenta.saldo = deposito.realizarDeposito(montoDeposito);
                    
                    JOptionPane.showMessageDialog(null,lines + "\nDeposito Exitoso.\n" + lines);
                    break;
                case 3:
                    double montoRetiro;
                    montoRetiro = Double.parseDouble(JOptionPane.showInputDialog(null,lines + "\nIngrese el monto a retirar\n" + lines));
                    Retiro retiro = new Retiro(cuenta.saldo);
                    cuenta.saldo = retiro.realizarRetiro(montoRetiro);
                    JOptionPane.showMessageDialog(null,lines + "\nRetiro cancelado/exitoso (verifique saldo).\n" + lines);
                    break;
                case 4:
                    JOptionPane.showMessageDialog(null,lines + "\nGracias por utilizar nuestros cajeros\n" + lines);
                    return;
                default:
                    JOptionPane.showMessageDialog(null,lines + "\nOpción inválida. Intentalo de nuevo\n" + lines);
            }
        }
    }
}
