<Global.Microsoft.VisualBasic.CompilerServices.DesignerGenerated()>
Partial Class Form1
    Inherits System.Windows.Forms.Form

    'Form overrides dispose to clean up the component list.
    <System.Diagnostics.DebuggerNonUserCode()>
    Protected Overrides Sub Dispose(ByVal disposing As Boolean)
        Try
            If disposing AndAlso components IsNot Nothing Then
                components.Dispose()
            End If
        Finally
            MyBase.Dispose(disposing)
        End Try
    End Sub

    'Required by the Windows Form Designer
    Private components As System.ComponentModel.IContainer

    'NOTE: The following procedure is required by the Windows Form Designer
    'It can be modified using the Windows Form Designer.  
    'Do not modify it using the code editor.
    <System.Diagnostics.DebuggerStepThrough()>
    Private Sub InitializeComponent()
        Dim resources As System.ComponentModel.ComponentResourceManager = New System.ComponentModel.ComponentResourceManager(GetType(Form1))
        Me.map = New System.Windows.Forms.PictureBox()
        Me.spartan = New System.Windows.Forms.PictureBox()
        Me.TextBox2 = New System.Windows.Forms.TextBox()
        CType(Me.map, System.ComponentModel.ISupportInitialize).BeginInit()
        CType(Me.spartan, System.ComponentModel.ISupportInitialize).BeginInit()
        Me.SuspendLayout()
        '
        'map
        '
        Me.map.Image = CType(resources.GetObject("map.Image"), System.Drawing.Image)
        Me.map.Location = New System.Drawing.Point(-3, -4)
        Me.map.Name = "map"
        Me.map.Size = New System.Drawing.Size(450, 450)
        Me.map.SizeMode = System.Windows.Forms.PictureBoxSizeMode.StretchImage
        Me.map.TabIndex = 0
        Me.map.TabStop = False
        '
        'spartan
        '
        Me.spartan.Anchor = System.Windows.Forms.AnchorStyles.None
        Me.spartan.Image = Global.WindowsApplication1.My.Resources.Resources.spartan1
        Me.spartan.Location = New System.Drawing.Point(196, 12)
        Me.spartan.Name = "spartan"
        Me.spartan.Size = New System.Drawing.Size(50, 50)
        Me.spartan.SizeMode = System.Windows.Forms.PictureBoxSizeMode.StretchImage
        Me.spartan.TabIndex = 1
        Me.spartan.TabStop = False
        '
        'TextBox2
        '
        Me.TextBox2.Enabled = False
        Me.TextBox2.Location = New System.Drawing.Point(332, 410)
        Me.TextBox2.Name = "TextBox2"
        Me.TextBox2.ReadOnly = True
        Me.TextBox2.Size = New System.Drawing.Size(100, 20)
        Me.TextBox2.TabIndex = 3
        Me.TextBox2.TextAlign = System.Windows.Forms.HorizontalAlignment.Center
        '
        'Form1
        '
        Me.AutoScaleDimensions = New System.Drawing.SizeF(6.0!, 13.0!)
        Me.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font
        Me.ClientSize = New System.Drawing.Size(442, 442)
        Me.Controls.Add(Me.TextBox2)
        Me.Controls.Add(Me.spartan)
        Me.Controls.Add(Me.map)
        Me.Name = "Form1"
        Me.Text = "Form1"
        CType(Me.map, System.ComponentModel.ISupportInitialize).EndInit()
        CType(Me.spartan, System.ComponentModel.ISupportInitialize).EndInit()
        Me.ResumeLayout(False)
        Me.PerformLayout()

    End Sub

    Friend WithEvents map As PictureBox
    Friend WithEvents spartan As PictureBox
    Friend WithEvents TextBox2 As TextBox
End Class
