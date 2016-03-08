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
    Dim x As Double
    Dim y As Double
    Dim numx As Double = 0
    Dim numy As Double = 0

    Private Sub Form1_KeyDown(sender As Object, e As KeyEventArgs) Handles Me.KeyDown

        Select Case (e.KeyCode)
            Case Keys.Left
                angle -= 22.5 Mod 360
                x = ((map.Width / 2.5) * Math.Cos(getRadiansFromDegree(angle))) - spartan.Width / 2
                y = ((map.Height / 2.5) * Math.Sin(getRadiansFromDegree(angle))) - spartan.Height / 2
            Case Keys.Right
                angle += 22.5 Mod 360
                x = ((map.Width / 2.5) * Math.Cos(getRadiansFromDegree(angle))) - spartan.Width / 2
                y = ((map.Height / 2.5) * Math.Sin(getRadiansFromDegree(angle))) - spartan.Height / 2
            Case Keys.W
                spartan.Top -= 10
            Case Keys.S
                spartan.Top += 10
            Case Keys.A
                spartan.Left -= 10
            Case Keys.D
                spartan.Left += 10
        End Select

        ' spartan.Image = p1
        'spartan.Location() = New Point(x + Me.Width / 2, y + (Me.Height / 2) - (spartan.Height / 2))

        If angle < 0 Then angle = 360 - angle
        'Select Case (angle Mod 360)
        '    Case 0 To 44 ', -315 To -359
        '        spartan.Image = p1
        '        'spartan.Location = setPoint(2, 1)
        '        'spartan.Top -= spartan.Height
        '        TextBox1.Text = "case 1"
        '    Case 45 To 89 ', -270 To -314
        '        spartan.Image = p2
        '        TextBox1.Text = "case 2"
        '    'spartan.Location = setPoint(3, 1)
        '    Case 90 To 134 ', -225 To -269
        '        spartan.Image = p3
        '        'spartan.Location = setPoint(3, 2)
        '        'spartan.Left += spartan.Width
        '        TextBox1.Text = "case 3"
        '    Case 135 To 179 ', -180 To -224
        '        spartan.Image = p4
        '        'spartan.Location = setPoint(3, 3)
        '        TextBox1.Text = "case 4"
        '    Case 180 To 224 ', -135 To -179
        '        spartan.Image = p5
        '        'spartan.Location = setPoint(2, 3)
        '        'spartan.Top += spartan.Height
        '        TextBox1.Text = "case 5"
        '    Case 225 To 269 ', -90 To -134
        '        spartan.Image = p6
        '        'spartan.Location = setPoint(1, 3)
        '        TextBox1.Text = "case 6"
        '    Case 270 To 314 ', -45 To -89
        '        spartan.Image = p7
        '        'spartan.Location = setPoint(1, 2)
        '        'spartan.Left -= spartan.Width
        '        TextBox1.Text = "case 7"
        '    Case 315 To 359 ', 0 To -44
        '        spartan.Image = p8
        '        'spartan.Location = setPoint(1, 1)
        '        TextBox1.Text = "case 8"

        '        'Case Else
        '        'spartan.Image = p1
        '        'spartan.Location = setPoint(1, 1)
        'End Select

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
        Dim radians As Double = (degree * Math.PI) / 180.0
        Return radians
    End Function

    Private Sub Form1_Load(sender As Object, e As EventArgs) Handles MyBase.Load
        Me.Focus()
        spartan.Parent = map
        spartan.BackColor = Color.Transparent
        Timer1.Start()
    End Sub

    Private Sub Timer1_Tick(sender As Object, e As EventArgs) Handles Timer1.Tick
        Dim cx As Integer = Cursor.Position.X
        Dim cy As Integer = Cursor.Position.Y
        Dim cc As String = cx & "," & cy
        TextBox3.Text = cc

        If Cursor.Position.Y < (Me.Top + spartan.Top) And Cursor.Position.X > (Me.Left + spartan.Left) And Cursor.Position.X < (Me.Left + spartan.Left + spartan.Width) Then
            spartan.Image = p1
        ElseIf Cursor.Position.Y < (Me.Top + spartan.Top) And Cursor.Position.X > (Me.Left + spartan.Left + spartan.Width) Then
            spartan.Image = p2
        ElseIf Cursor.Position.Y > (Me.Top + spartan.Top) And Cursor.Position.Y < (Me.Top + spartan.Top + spartan.Height) And Cursor.Position.X > (Me.Left + spartan.Left + spartan.Width)
            spartan.Image = p3
        ElseIf Cursor.Position.Y > (Me.Top + spartan.Top) And Cursor.Position.X > (Me.Left + spartan.Left + spartan.Width)
            spartan.Image = p4
        ElseIf Cursor.Position.Y > (Me.Top + spartan.Top) And Cursor.Position.X > (Me.Left + spartan.Left) And Cursor.Position.X < (Me.Left + spartan.Left + spartan.Width)
            spartan.Image = p5
        ElseIf Cursor.Position.Y > (Me.Top + spartan.Top + spartan.Height) And Cursor.Position.X < (Me.Left + spartan.Left)
            spartan.Image = p6
        ElseIf Cursor.Position.Y > (Me.Top + spartan.Top) And Cursor.Position.Y < (Me.Top + spartan.Top + spartan.Height) And Cursor.Position.X < (Me.Left + spartan.Left)
            spartan.Image = p7
        ElseIf Cursor.Position.Y < (Me.Top + spartan.Top) And Cursor.Position.X < (Me.Left + spartan.Left)
            spartan.Image = p8
        End If
 
    End Sub

    Private Sub map_Click(sender As Object, e As EventArgs) Handles map.Click

    End Sub
End Class
