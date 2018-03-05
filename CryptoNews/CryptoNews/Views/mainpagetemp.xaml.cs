using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace CryptoNews.Views
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class mainpagetemp : ContentPage
    {
        public mainpagetemp()
        {
            InitializeComponent();
        }

        private void SearchPageSample_Clicked(object sender, System.EventArgs e)
        {
            Navigation.PushAsync(new Page1());
        }
    }
}