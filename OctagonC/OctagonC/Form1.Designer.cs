namespace WindowsFormsApplication2
{
    partial class Form1
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(Form1));
            this.TextBox3 = new System.Windows.Forms.TextBox();
            this.TextBox1 = new System.Windows.Forms.TextBox();
            this.TextBox2 = new System.Windows.Forms.TextBox();
            this.spartan = new System.Windows.Forms.PictureBox();
            this.map = new System.Windows.Forms.PictureBox();
            this.timer1 = new System.Windows.Forms.Timer(this.components);
            this.timer2 = new System.Windows.Forms.Timer(this.components);
            ((System.ComponentModel.ISupportInitialize)(this.spartan)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.map)).BeginInit();
            this.SuspendLayout();
            // 
            // TextBox3
            // 
            this.TextBox3.BackColor = System.Drawing.Color.White;
            this.TextBox3.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
            this.TextBox3.Enabled = false;
            this.TextBox3.ForeColor = System.Drawing.Color.White;
            this.TextBox3.Location = new System.Drawing.Point(174, 300);
            this.TextBox3.Name = "TextBox3";
            this.TextBox3.ReadOnly = true;
            this.TextBox3.Size = new System.Drawing.Size(100, 20);
            this.TextBox3.TabIndex = 10;
            this.TextBox3.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            // 
            // TextBox1
            // 
            this.TextBox1.Enabled = false;
            this.TextBox1.Location = new System.Drawing.Point(187, 258);
            this.TextBox1.Name = "TextBox1";
            this.TextBox1.Size = new System.Drawing.Size(62, 20);
            this.TextBox1.TabIndex = 9;
            // 
            // TextBox2
            // 
            this.TextBox2.BackColor = System.Drawing.Color.White;
            this.TextBox2.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
            this.TextBox2.Enabled = false;
            this.TextBox2.ForeColor = System.Drawing.Color.White;
            this.TextBox2.Location = new System.Drawing.Point(174, 215);
            this.TextBox2.Name = "TextBox2";
            this.TextBox2.ReadOnly = true;
            this.TextBox2.Size = new System.Drawing.Size(100, 20);
            this.TextBox2.TabIndex = 8;
            this.TextBox2.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            // 
            // spartan
            // 
            this.spartan.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.spartan.BackColor = System.Drawing.SystemColors.Control;
            this.spartan.Image = ((System.Drawing.Image)(resources.GetObject("spartan.Image")));
            this.spartan.Location = new System.Drawing.Point(199, 16);
            this.spartan.Name = "spartan";
            this.spartan.Size = new System.Drawing.Size(50, 50);
            this.spartan.SizeMode = System.Windows.Forms.PictureBoxSizeMode.StretchImage;
            this.spartan.TabIndex = 7;
            this.spartan.TabStop = false;
            // 
            // map
            // 
            this.map.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.map.Image = ((System.Drawing.Image)(resources.GetObject("map.Image")));
            this.map.Location = new System.Drawing.Point(0, 0);
            this.map.Name = "map";
            this.map.Size = new System.Drawing.Size(450, 450);
            this.map.SizeMode = System.Windows.Forms.PictureBoxSizeMode.StretchImage;
            this.map.TabIndex = 6;
            this.map.TabStop = false;
            // 
            // timer1
            // 
            this.timer1.Enabled = true;
            this.timer1.Interval = 10;
            this.timer1.Tick += new System.EventHandler(this.timer1_Tick);
            // 
            // timer2
            // 
            this.timer2.Interval = 10;
            this.timer2.Tick += new System.EventHandler(this.timer2_Tick);
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(450, 450);
            this.Controls.Add(this.TextBox3);
            this.Controls.Add(this.TextBox1);
            this.Controls.Add(this.TextBox2);
            this.Controls.Add(this.spartan);
            this.Controls.Add(this.map);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.None;
            this.Name = "Form1";
            this.Text = "Form1";
            this.KeyDown += new System.Windows.Forms.KeyEventHandler(this.ReadKey);
            this.KeyUp += new System.Windows.Forms.KeyEventHandler(this.ReadKeyUp);
            ((System.ComponentModel.ISupportInitialize)(this.spartan)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.map)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        internal System.Windows.Forms.TextBox TextBox3;
        internal System.Windows.Forms.TextBox TextBox1;
        internal System.Windows.Forms.TextBox TextBox2;
        internal System.Windows.Forms.PictureBox spartan;
        internal System.Windows.Forms.PictureBox map;
        public System.Windows.Forms.Timer timer1;
        private System.Windows.Forms.Timer timer2;
    }
}

