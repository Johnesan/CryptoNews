using CryptoNews.Models;
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
    public partial class EditBlogWebsite : ContentPage
    {
        public EditBlogWebsite()
        {
            InitializeComponent();
            BindingContext = new BlogWebsite();
        }
    
        private async void SaveButtonClicked(object sender, EventArgs e)
        {
            var blogWebsite = (BlogWebsite)BindingContext;
            App.database.UpdateBlogWebsite(blogWebsite);
            await Navigation.PopAsync();
            
        }
    }
}