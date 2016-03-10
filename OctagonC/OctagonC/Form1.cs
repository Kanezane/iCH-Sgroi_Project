using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Drawing.Drawing2D;

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
        private static Image octa;
        double angle;
        bool W, A, S, D = false;
        private Bitmap spartano;
        private Graphics gfx;
        private int x = 199;
        private int y = 16;
        private int direction = 1;

        public Form1() {
            p1 = Image.FromFile(fcp.find() + "spartan1.png");
            p2 = Image.FromFile(fcp.find() + "spartan2.png");
            p3 = Image.FromFile(fcp.find() + "spartan3.png");
            p4 = Image.FromFile(fcp.find() + "spartan4.png");
            p5 = Image.FromFile(fcp.find() + "spartan5.png");
            p6 = Image.FromFile(fcp.find() + "spartan6.png");
            p7 = Image.FromFile(fcp.find() + "spartan7.png");
            p8 = Image.FromFile(fcp.find() + "spartan8.png");
            octa = Image.FromFile(fcp.find() + "map.png");

            InitializeComponent();
            map.Controls.Add(spartan);
            spartan.BackColor = Color.Transparent;
            this.Focus();
            timer1.Start();
            timer2.Start();
        }

        private void Form1_Load(object sender, EventArgs e)
        {
        }
       
        void ReadKey(object sender, KeyEventArgs e)
        {
            switch ((e.KeyCode))
            {
                case Keys.W:
                    W = true;
                    break;
                case Keys.S:
                    S = true;
                    break;
                case Keys.A:
                    A = true;
                    break;
                case Keys.D:
                    D = true;
                    break;
                    
                    //angle -= 22.5 % 360;
                    //x = ((map.Width / 2.5) * Math.Cos(getRadiansFromDegree(angle-90))) - spartan.Width / 2;
                    //y = ((map.Height / 2.5) * Math.Sin(getRadiansFromDegree(angle-90)));

                    //spartan.Location = new Point(Convert.ToInt16(x + Width / 2), Convert.ToInt16(y + (Height / 2) - (spartan.Height / 2)));
            }

            if (angle < 0)
            {
                angle = 360 - angle;
            }

            TextBox2.Text = "angle: " + angle % 360;
            spartan.Refresh();
        }

        private void timer1_Tick(object sender, EventArgs e)
        {
            int cx = Cursor.Position.X;
            int cy = Cursor.Position.Y;
            string cc = cx + "," + cy;
            TextBox3.Text = cc;
            
            if (Cursor.Position.Y < (this.Top + spartan.Top) 
              & Cursor.Position.X > (this.Left + spartan.Left)                     // Compreso tra la X iniziale e finale dello spartan
              & Cursor.Position.X < (this.Left + spartan.Left + spartan.Width))    // Compreso tra la X iniziale e finale dello spartan
            {
                direction = 1;
                //spartan.Image = p1;
            }

            else if (Cursor.Position.Y < (this.Top + spartan.Top) 
                   & Cursor.Position.X > (this.Left + spartan.Left + spartan.Width))
            {
                direction = 2;
                //spartan.Image = p2;
            }

            else if (Cursor.Position.Y > (this.Top + spartan.Top)                    // Compreso tra la Y iniziale e finale dello spartan
                   & Cursor.Position.Y < (this.Top + spartan.Top + spartan.Height)      // Compreso tra la Y iniziale e finale dello spartan
                   & Cursor.Position.X > (this.Left + spartan.Left + spartan.Width))
            {
                direction = 3;
                //spartan.Image = p3;
            }

            else if (Cursor.Position.Y > (this.Top + spartan.Top) 
                   & Cursor.Position.X > (this.Left + spartan.Left + spartan.Width))
            {
                direction = 4;
                //spartan.Image = p4;
            }

            else if (Cursor.Position.Y > (this.Top + spartan.Top+spartan.Height) 
                   & Cursor.Position.X > (this.Left + spartan.Left)                     // Compreso tra la X iniziale e finale dello spartan
                   & Cursor.Position.X < (this.Left + spartan.Left + spartan.Width))    // Compreso tra la X iniziale e finale dello spartan
            {
                direction = 5;
                //spartan.Image = p5;
            }

            else if (Cursor.Position.Y > (this.Top + spartan.Top + spartan.Height) 
                   & Cursor.Position.X < (this.Left + spartan.Left))
            {
                direction = 6;
                //spartan.Image = p6;
            }

            else if (Cursor.Position.Y > (this.Top + spartan.Top)                    // Compreso tra la Y iniziale e finale dello spartan
                   & Cursor.Position.Y < (this.Top + spartan.Top + spartan.Height)      // Compreso tra la Y iniziale e finale dello spartan
                   & Cursor.Position.X < (this.Left + spartan.Left))
            {
                direction = 7;
                //spartan.Image = p7;
            }

            else if (Cursor.Position.Y < (this.Top + spartan.Top) 
                   & Cursor.Position.X < (this.Left + spartan.Left))
            {
                direction = 8;
                //spartan.Image = p8;
            }

            creaSpartano(new Bitmap(fcp.find() + "spartan" + direction + ".png"), x, y);


        }
        private object setPoint(int mx, int my)
        {
            int x = Convert.ToInt16(((this.Width / 4) * mx) - (spartan.Width / 1.4));
            int y = Convert.ToInt16(((this.Height / 4) * my) - (spartan.Height / 1.4));
            return new Point(x, y);
        }

        private double getRadiansFromDegree(double degree)
        {
            double radians = (degree * Math.PI) / 180;
            return radians;
        }
        void ReadKeyUp(object sender, KeyEventArgs e)
        {
            switch (e.KeyCode)
            {
                case Keys.W:
                    W = false;
                    break;
                case Keys.S:
                    S = false;
                    break;
                case Keys.A:
                    A = false;
                    break;
                case Keys.D:
                    D = false;
                    break;
            }
            TextBox1.Text = Convert.ToString(direction);
            //creaSpartano(new Bitmap(fcp.find() + "spartan" + direction + ".png"), x, y);//spartan.Top -= 10;

        }

        private void timer2_Tick(object sender, EventArgs e)
        {
            if (W) y -= 10; 
            if (A) x -= 10; //spartan.Left -= 10;
            if (S) y += 10; //spartan.Top += 10;
            if (D) x += 10; //spartan.Left += 10;
        }

        private Graphics creaSpartano(Bitmap spartano, int x, int y) {
            gfx = map.CreateGraphics();
            //gfx.Clear(Form1.ActiveForm.BackColor);
            gfx.DrawImage(spartano, new Rectangle(x, y, spartano.Width/8, spartano.Height/8));
            return gfx;
        }
    }
}
