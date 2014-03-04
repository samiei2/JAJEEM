using System;
using System.Collections.Generic;
using System.Linq;
using System.Windows.Forms;
using System.Runtime.InteropServices;


namespace LockC2
{
    static class Program
    {
        [DllImport("acmain.dll")]
        public extern static int LckFunc5862200(string s1, int s2, string s3);

        [DllImport("acmain.dll")]
        public extern static string LckFunc4457744(string s1, int s2, string s3);

        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main()
        {

            try
            {
           /*     string VID = HamYar7145763("4C7A680C-E1E1DC40B1E4", 234450225, "");
                if (VID == "C5.S7.P9.W5.X1.N6.Q8.Z5.M6.A4")
                {
            * */
                int ActID = LckFunc5862200("F66192B0-B2F5ED2806DF", 518052876, "");
                    if (ActID == 100)
                    {

                        string VID = LckFunc4457744("F66192B0-B2F5ED2806DF", 518052876, "");

                        Application.EnableVisualStyles();
                        Application.SetCompatibleTextRenderingDefault(false);
                        Application.Run(new Form1());
                    }
                    else
                    {
                        Application.Exit();
                    }
     //           }
            }
            catch (Exception ex1)
            {
                MessageBox.Show(ex1.Message);

                Application.Exit();
            }

        }
    }
}
