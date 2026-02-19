

# Je selectionne le premier port COM de mon PC
SPORTCOM = ([System.IO.Ports.SerialPort] :: GetPortNames())[o]

SBAUDRATE = 9600
SParity
Sdatabits = 8
Sstopbits = [System.IO.Ports.StopBits] :: One

Sport = [System.IO.Ports.SerialPort]: :new(SPORTCOM, SBAUDRATE, SParity, Sdatabits, Sstopbits)
Sport.readtimeout = 1000
Sport.Open()
Sport.writeline("'n") ; write-host Sport.ReadLine()
Sport.writeline("en") ; write-host Sport.ReadLine()
Sport.writeline("conf t") ; write-host Sport.ReadLine()
Sport.writeline("int fa0/5") ; write-host Sport.ReadLine()
Sport.writeline("sh") ; write-host Sport.ReadLine()

Sport.Close()
Sport.Dispose()