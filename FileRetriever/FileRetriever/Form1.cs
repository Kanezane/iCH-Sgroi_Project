using System;
using System.Windows.Forms;
using System.Collections.Generic;
using System.Drawing;

namespace FileRetriever {
    public partial class Form1 : Form {

        private IUrlRetriever urlRetriever = new UrlRetriever();

        public Form1() {
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e) {
            TraktTest t = new TraktTest();
            t.PerformQuery();

            listView1.Items.Clear();

            List<RowValue> retrievedRows = urlRetriever.retrieve(textBox1.Text, 10);

            foreach (RowValue row in retrievedRows) {
                listView1.Items.Add(row.getTitle());
                listView1.Items.Add(row.getUrl());
            }

            setListViewStyle();
        }

        private void setListViewStyle() {
            listView1.Scrollable = true;
            listView1.View = View.Details;

            ColumnHeader header = new ColumnHeader();
            header.Text = "";
            header.Name = "dummyHeader";
            listView1.Columns.Add(header);
            listView1.AutoResizeColumns(ColumnHeaderAutoResizeStyle.HeaderSize);
            listView1.HeaderStyle = ColumnHeaderStyle.None;

            if (listView1.Items.Count != 0) {
                for (int i = 0; i < listView1.Items.Count - 1; i++) {
                    if (listView1.Items[i].Index % 2 == 0) {
                        listView1.Items[i].BackColor = Color.LightGray;
                    } else {
                        listView1.Items[i].BackColor = Color.White;
                    }
                }
            }
            listView1.ItemSelectionChanged += listView1_ItemSelectionChanged;
        }
 
        
        private void listView1_ItemSelectionChanged(object sender, ListViewItemSelectionChangedEventArgs e) {
            if (e.Item.BackColor == Color.LightGray) {
                e.Item.Selected = false;
                e.Item.Focused = false;
            }
        }

        private void listView1_SelectedIndexChanged(object sender, EventArgs e) {
            ListView.SelectedIndexCollection indexes = this.listView1.SelectedIndices;

            foreach (int index in indexes) {
                textBox2.Text = listView1.Items[index].Text;
            }
        }
    }
}
