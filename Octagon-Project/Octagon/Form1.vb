Public Class Form1
    Dim angle As Double = 0
    Dim findClassPath As New FindClassPath
    Dim p1 As Image = Image.FromFile(findClassPath.find & "\Resources\spartan1.png")
    Dim p2 As Image = Image.FromFile(findClassPath.find & "\Resources\spartan2.png")
    Dim p3 As Image = Image.FromFile(findClassPath.find & "\Resources\spartan3.png")
    Dim p4 As Image = Image.FromFile(findClassPath.find & "\Resources\spartan4.png")
    Dim p5 As Image = Image.FromFile(findClassPath.find & "\Resources\spartan5.png")
    Dim p6 As Image = Image.FromFile(findClassPath.find & "\Resources\spartan6.png")
    Dim p7 As Image = Image.FromFile(findClassPath.find & "\Resources\spartan7.png")
    Dim p8 As Image = Image.FromFile(findClassPath.find & "\Resources\spartan8.png")
    Dim x As Double = 0
    Dim y As Double = 0
    Dim numx As Double = 0
    Dim numy As Double = 0

    Private Sub Form1_KeyDown(sender As Object, e As KeyEventArgs) Handles Me.KeyDown

        Select Case (e.KeyCode)
            'Case Keys.Left
            '    angle = (angle - 45) Mod 360
            'Case Keys.Right
            '    angle = (angle + 45) Mod 360
            Case Keys.Left
                angle -= 10 Mod 360
                x = Me.Width / 3 * Math.Cos(getRadiansFromDegree(angle))
                y = Me.Height / 3 * Math.Sin(getRadiansFromDegree(angle))
            Case Keys.Right
                angle += 10 Mod 360
                x = Me.Width / 3 * Math.Cos(getRadiansFromDegree(angle))
                y = Me.Height / 3 * Math.Sin(getRadiansFromDegree(angle))
        End Select

        spartan.Image = p1
        spartan.Location() = New Point(x, y)

        Select Case (angle Mod 360)
            Case >= 45 < 90, > -270 <= -315
                spartan.Image= p2
                TextBox1.Text="case 1"
                'spartan.Location = setPoint(3, 1)
            Case >= 90 < 135, > -225 <= -270
                spartan.Image = p3
                'spartan.Location = setPoint(3, 2)
                'spartan.Left += spartan.Width
                TextBox1.Text = "case 2"
            Case >= 135 < 180, > -180 <= -225
                spartan.Image = p4
                spartan.Location = setPoint(3, 3)
                TextBox1.Text = "case 3"
            Case >= 180 < 225, > -135 <= -180
                spartan.Image = p5
                'spartan.Location = setPoint(2, 3)
                'spartan.Top += spartan.Height
                TextBox1.Text = "case 4"
            Case >= 225 < 270, > -90 <= -135
                spartan.Image = p6
                'spartan.Location = setPoint(1, 3)
                TextBox1.Text = "case 5"
            Case >= 270 < 315, > -45 <= -90
                spartan.Image = p7
                'spartan.Location = setPoint(1, 2)
                'spartan.Left -= spartan.Width
                TextBox1.Text = "case 6"
            Case >= 315 < 360, > 0 <= -45
                spartan.Image = p8
                'spartan.Location = setPoint(1, 1)
                TextBox1.Text = "case 7"
            Case >= 0 < 45, >= -315 < -360
                spartan.Image = p1
                'spartan.Location = setPoint(2, 1)
                'spartan.Top -= spartan.Height
                TextBox1.Text = "case 8"
            Case Else
                spartan.Image = p1
                'spartan.Location = setPoint(1, 1)
        End Select

        TextBox2.Text = "angle: " & angle Mod 360
        'spartan.Left += spartan.Width / 7
        'spartan.Top -= spartan.Height / 7
        spartan.Refresh()
    End Sub

    Private Function setPoint(mx As Integer, my As Integer)
        Dim x As Integer = ((Me.Width / 4) * mx) - (spartan.Width / 1.4)
        Dim y As Integer = ((Me.Height / 4) * my) - (spartan.Height / 1.4)
        Return New Point(x, y)
    End Function

    Private Function getRadiansFromDegree(degree As Double) As Double
        Dim radians As Double = (degree * Math.PI) / 180
        Return radians
    End Function

    Private Sub Form1_Load(sender As Object, e As EventArgs) Handles MyBase.Load
        Me.Focus()
        spartan.Parent = map
        spartan.BackColor = Color.Transparent
    End Sub
End Class
