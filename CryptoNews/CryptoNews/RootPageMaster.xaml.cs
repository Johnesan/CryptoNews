using CryptoNews.Views;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Linq;
using System.Runtime.CompilerServices;
using System.Text;
using System.Threading.Tasks;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace CryptoNews
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class RootPageMaster : ContentPage
    {
        public ListView ListView;

        public RootPageMaster()
        {
            InitializeComponent();

            BindingContext = new RootPageMasterViewModel();
            ListView = MenuItemsListView;
            ListView.SeparatorVisibility = Xamarin.Forms.SeparatorVisibility.Default;
            ListView.SeparatorColor = Color.FromHex("C8C7CC");
        }

        class RootPageMasterViewModel : INotifyPropertyChanged
        {
            public ObservableCollection<RootPageMenuItem> MenuItems { get; set; }

            public RootPageMasterViewModel()
            {
                MenuItems = new ObservableCollection<RootPageMenuItem>(new[]
                {
                    new RootPageMenuItem { Id = 0, Title = "Latest Crypto News", TargetType = typeof(BlogPosts) },
                    new RootPageMenuItem { Id = 1, Title = "My Favourites", TargetType = typeof(FavouriteBlogPosts) },
                    new RootPageMenuItem { Id = 2, Title = "Manage Blog Websites", TargetType = typeof(BlogWebsites) }
                
                });
            }

            #region INotifyPropertyChanged Implementation
            public event PropertyChangedEventHandler PropertyChanged;
            void OnPropertyChanged([CallerMemberName] string propertyName = "")
            {
                if (PropertyChanged == null)
                    return;

                PropertyChanged.Invoke(this, new PropertyChangedEventArgs(propertyName));
            }
            #endregion
        }
    }
}