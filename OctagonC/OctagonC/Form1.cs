using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace WindowsFormsApplication2{
    
    public partial class Form1 : Form
    {
        private static FindClassPath fcp = new FindClassPath();
        private static Image p1;
        private static Image p2;
        private static Image p3;
        private static Image p4;
        private static Image p5;
        private static Image p6;
        private static Image p7;
        private static Image p8;
        double angle = 0;
        double x;
        double y;

        public Form1() {
            p1 = Image.FromFile(fcp.find() + "\\Resources\\spartan1.png");
            p2 = Image.FromFile(fcp.find() + "\\Resources\\spartan2.png");
            p3 = Image.FromFile(fcp.find() + "\\Resources\\spartan3.png");
            p4 = Image.FromFile(fcp.find() + "\\Resources\\spartan4.png");
            p5 = Image.FromFile(fcp.find() + "\\Resources\\spartan5.png");
            p6 = Image.FromFile(fcp.find() + "\\Resources\\spartan6.png");
            p7 = Image.FromFile(fcp.find() + "\\Resources\\spartan7.png");
            p8 = Image.FromFile(fcp.find() + "\\Resources\\spartan8.png");

            InitializeComponent();
            map.Controls.Add(spartan);
            spartan.BackColor = Color.Transparent;
            this.Focus();
            Timer1.Start();
        }

        private void Form1_Load(object sender, EventArgs e){}

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
            if (angle < 0){
                angle = 360 - angle;
            }

            TextBox2.Text = "angle: " + angle % 360;
            spartan.Refresh();
        }

        private void Timer1_Tick(object sender, EventArgs e){
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
        private object setPoint(int mx, int my) {
            int x = Convert.ToInt16(((this.Width / 4) * mx) - (spartan.Width / 1.4));
            int y = Convert.ToInt16(((this.Height / 4) * my) - (spartan.Height / 1.4));
            return new Point(x, y);
        }
        
        private double getRadiansFromDegree(double degree) {
            double radians = (degree * Math.PI) / 180;
            return radians;
            
        }
    }
}
