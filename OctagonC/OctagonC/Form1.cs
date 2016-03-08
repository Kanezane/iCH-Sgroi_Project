using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace WindowsFormsApplication2
{
    public partial class Form1 : Form
    {
        double angle = 0;
        //FindClassPath findClassPath = new FindClassPath();

        Image p1 = Image.FromFile(Environment.CurrentDirectory + "\\Resources\\spartan1.png");
        Image p2 = Image.FromFile(Environment.CurrentDirectory + "\\Resources\\spartan2.png");
        Image p3 = Image.FromFile(Environment.CurrentDirectory + "\\Resources\\spartan3.png");
        Image p4 = Image.FromFile(Environment.CurrentDirectory + "\\Resources\\spartan4.png");
        Image p5 = Image.FromFile(Environment.CurrentDirectory + "\\Resources\\spartan5.png");
        Image p6 = Image.FromFile(Environment.CurrentDirectory + "\\Resources\\spartan6.png");
        Image p7 = Image.FromFile(Environment.CurrentDirectory + "\\Resources\\spartan7.png");
        Image p8 = Image.FromFile(Environment.CurrentDirectory + "\\Resources\\spartan8.png");
        double x;
        double y;
        //double numx = 0;
        //double numy = 0;

        public Form1()
        {
            InitializeComponent();
            map.Controls.Add(spartan);
            spartan.BackColor = Color.Transparent;
            this.Focus();
            Timer1.Start();
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            
        }

        void ReadKey(object sender, KeyEventArgs e)
        {
            switch ((e.KeyCode))
            {
                
                case Keys.Left:
                    angle -= 22.5 % 360;
                    x = ((map.Width / 2.5) * Math.Cos(getRadiansFromDegree(angle-90))) - spartan.Width / 2;
                    y = ((map.Height / 2.5) * Math.Sin(getRadiansFromDegree(angle-90)));
                    
                    spartan.Location = new Point(Convert.ToInt16(x + Width / 2), Convert.ToInt16(y + (Height / 2) - (spartan.Height / 2)));
                    break;
                case Keys.Right:
                    angle += 22.5 % 360;
                    x = ((map.Width / 2.5) * Math.Cos(getRadiansFromDegree(angle-90))) - spartan.Width / 2;
                    y = ((map.Height / 2.5) * Math.Sin(getRadiansFromDegree(angle-90)));

                    spartan.Location = new Point(Convert.ToInt16(x + Width / 2), Convert.ToInt16(y + (Height / 2) - (spartan.Height / 2)));
                    break;
                /*
                case Keys.W:
                    spartan.Top -= 10;
                    break;
                case Keys.S:
                    spartan.Top += 10;
                    break;
                case Keys.A:
                    spartan.Left -= 10;
                    break;
                case Keys.D:
                    spartan.Left += 10;
                    break;
                    */
            }
            if (angle < 0)
                angle = 360 - angle;
            /*Select Case (angle Mod 360)
                Case 0 To 44 ', -315 To -359
                    spartan.Image = p1
                    'spartan.Location = setPoint(2, 1)
                    'spartan.Top -= spartan.Height
                    TextBox1.Text = "case 1"
                Case 45 To 89 ', -270 To -314
                    spartan.Image = p2
                    TextBox1.Text = "case 2"
                'spartan.Location = setPoint(3, 1)
                Case 90 To 134 ', -225 To -269
                    spartan.Image = p3
                    'spartan.Location = setPoint(3, 2)
                    'spartan.Left += spartan.Width
                    TextBox1.Text = "case 3"
                Case 135 To 179 ', -180 To -224
                    spartan.Image = p4
                    'spartan.Location = setPoint(3, 3)
                    TextBox1.Text = "case 4"
                Case 180 To 224 ', -135 To -179
                    spartan.Image = p5
                    'spartan.Location = setPoint(2, 3)
                    'spartan.Top += spartan.Height
                    TextBox1.Text = "case 5"
                Case 225 To 269 ', -90 To -134
                    spartan.Image = p6
                    'spartan.Location = setPoint(1, 3)
                    TextBox1.Text = "case 6"
                Case 270 To 314 ', -45 To -89
                    spartan.Image = p7
                    'spartan.Location = setPoint(1, 2)
                    'spartan.Left -= spartan.Width
                    TextBox1.Text = "case 7"
                Case 315 To 359 ', 0 To -44
                    spartan.Image = p8
                    'spartan.Location = setPoint(1, 1)
                    TextBox1.Text = "case 8"

                    'Case Else
                    'spartan.Image = p1
                    'spartan.Location = setPoint(1, 1)
            End Select
            */

            TextBox2.Text = "angle: " + angle % 360;
            spartan.Refresh();
        }
        private void Timer1_Tick(object sender, EventArgs e)
        {
            int cx = Cursor.Position.X;
            int cy = Cursor.Position.Y;
            string cc = cx + "," + cy;
            TextBox3.Text = cc;
            
            if (Cursor.Position.Y < (this.Top + spartan.Top) 
                & Cursor.Position.X > (this.Left + spartan.Left)                     // Compreso tra la X iniziale e finale dello spartan
                & Cursor.Position.X < (this.Left + spartan.Left + spartan.Width))    // Compreso tra la X iniziale e finale dello spartan
            {spartan.Image = p1;}

            else if (Cursor.Position.Y < (this.Top + spartan.Top) 
                & Cursor.Position.X > (this.Left + spartan.Left + spartan.Width))
            {spartan.Image = p2;}

            else if (Cursor.Position.Y > (this.Top + spartan.Top)                    // Compreso tra la Y iniziale e finale dello spartan
                & Cursor.Position.Y < (this.Top + spartan.Top + spartan.Height)      // Compreso tra la Y iniziale e finale dello spartan
                & Cursor.Position.X > (this.Left + spartan.Left + spartan.Width))
            {spartan.Image = p3;}

            else if (Cursor.Position.Y > (this.Top + spartan.Top) 
                & Cursor.Position.X > (this.Left + spartan.Left + spartan.Width))
            {spartan.Image = p4;}

            else if (Cursor.Position.Y > (this.Top + spartan.Top+spartan.Height) 
                & Cursor.Position.X > (this.Left + spartan.Left)                     // Compreso tra la X iniziale e finale dello spartan
                & Cursor.Position.X < (this.Left + spartan.Left + spartan.Width))    // Compreso tra la X iniziale e finale dello spartan
            {spartan.Image = p5;}

            else if (Cursor.Position.Y > (this.Top + spartan.Top + spartan.Height) 
                & Cursor.Position.X < (this.Left + spartan.Left))
            {spartan.Image = p6;}

            else if (Cursor.Position.Y > (this.Top + spartan.Top)                    // Compreso tra la Y iniziale e finale dello spartan
                & Cursor.Position.Y < (this.Top + spartan.Top + spartan.Height)      // Compreso tra la Y iniziale e finale dello spartan
                & Cursor.Position.X < (this.Left + spartan.Left))
            {spartan.Image = p7;}

            else if (Cursor.Position.Y < (this.Top + spartan.Top) 
                & Cursor.Position.X < (this.Left + spartan.Left))
            {spartan.Image = p8;}
            
        }
        private object setPoint(int mx, int my)
        {
            int x = Convert.ToInt16(((this.Width / 4) * mx) - (spartan.Width / 1.4));
            int y = Convert.ToInt16(((this.Height / 4) * my) - (spartan.Height / 1.4));
            return new Point(x, y);
        }
        
        private double getRadiansFromDegree(double degree)
        {
            double radians = (degree * Math.PI) / 180.0;
            return radians;
            
        }

    }
}
