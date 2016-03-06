Public Class Form1
    Dim angle As Integer = 0
    Dim findClassPath As New findClassPath
    Dim p1 As Image = Image.FromFile(findClassPath.find & "\Resources\spartan1.png")
    Dim p2 As Image = Image.FromFile(findClassPath.find & "\Resources\spartan2.png")
    Dim p3 As Image = Image.FromFile(findClassPath.find & "\Resources\spartan3.png")
    Dim p4 As Image = Image.FromFile(findClassPath.find & "\Resources\spartan4.png")
    Dim p5 As Image = Image.FromFile(findClassPath.find & "\Resources\spartan5.png")
    Dim p6 As Image = Image.FromFile(findClassPath.find & "\Resources\spartan6.png")
    Dim p7 As Image = Image.FromFile(findClassPath.find & "\Resources\spartan7.png")
    Dim p8 As Image = Image.FromFile(findClassPath.find & "\Resources\spartan8.png")

    Private Sub Form1_KeyDown(sender As Object, e As KeyEventArgs) Handles Me.KeyDown

        Select Case (e.KeyCode)
            Case Keys.Left
                angle = (angle - 45) Mod 360
            Case Keys.Right
                angle = (angle + 45) Mod 360
        End Select

        Select Case (angle)
            Case 45, -315
                spartan.Image = p2
                spartan.Location = setPoint(3, 1)
            Case 90, -270
                spartan.Image = p3
                spartan.Location = setPoint(3, 2)
                spartan.Left += spartan.Width
            Case 135, -225
                spartan.Image = p4
                spartan.Location = setPoint(3, 3)
            Case 180, -180
                spartan.Image = p5
                spartan.Location = setPoint(2, 3)
                spartan.Top += spartan.Height
            Case 225, -135
                spartan.Image = p6
                spartan.Location = setPoint(1, 3)
            Case 270, -90
                spartan.Image = p7
                spartan.Location = setPoint(1, 2)
                spartan.Left -= spartan.Width
            Case 315, -45
                spartan.Image = p8
                spartan.Location = setPoint(1, 1)
            Case 0, 360
                spartan.Image = p1
                spartan.Location = setPoint(2, 1)
                spartan.Top -= spartan.Height
            Case Else
                spartan.Image = p1
                spartan.Location = setPoint(1, 1)
        End Select

        TextBox2.Text = "angle: " & angle
        spartan.Refresh()
    End Sub

    Function setPoint(mx As Integer, my As Integer)
        Dim x As Integer = ((Me.Width / 4) * mx) - (spartan.Width / 1.4)
        Dim y As Integer = ((Me.Height / 4) * my) - (spartan.Height / 1.4)
        Return New Point(x, y)
    End Function

    Private Sub Form1_Load(sender As Object, e As EventArgs) Handles MyBase.Load
        Me.Focus()
        spartan.Parent = map
        spartan.BackColor = Color.Transparent
    End Sub
End Class
